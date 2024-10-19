pipeline {
    agent any

    environment {
        // 定义环境变量
        SERVER_IP = 'do001-why-ubuntu'                // 服务器IP地址
        SERVER_USER = 'root'                           // 服务器用户名
        TARGET_DIR = '/opt/module'                     // 服务器上的目标目录
        JAR_FILE = 'dmss.jar'                          // 打包后的文件名
        ZAP_DOCKER_IMAGE = 'zaproxy/zap-stable'       // ZAP Docker 镜像
        ZAP_PORT = '8081'                              // ZAP 监听的端口
        TARGET_URL = 'http://128.199.224.162:8080'    // 需要扫描的目标 URL
    }

    stages {
        stage('Clone Source Code') {
            steps {
                // 拉取项目源码
                git branch: 'master', url: 'https://github.com/allenwu-zhiwei/dmss.git'
            }
        }
        stage('Build Project') {
            steps {
                ansiColor('xterm') {
                    // 使用 Maven 构建项目
                    sh 'mvn clean package -DskipTests'
                }
            }
        }
        stage('Verify Build Output') {
            // 验证构建结果
            steps {
                sh 'ls -l target/'
            }
        }
        stage('Test') {
            steps {
                ansiColor('xterm') {
                    // 运行测试
                    sh 'mvn test'
                }
            }
        }
        stage('OWASP ZAP Scan') {
            steps {
                script {
                    // 定义时间戳
                    def timestamp = new Date().format("yyyyMMdd_HHmmss")
                    def reportFileName = "zap_report_${timestamp}.html"

                    // 在 Docker 中运行 ZAP 并扫描目标 URL，生成带时间戳的报告
                    sh """
                        docker run -t -u zap -p ${ZAP_PORT}:${ZAP_PORT} -v \$(pwd):/zap/wrk ${ZAP_DOCKER_IMAGE} zap.sh -cmd -quickurl ${TARGET_URL} -quickout /zap/wrk/${reportFileName}
                    """

                    // 将 ZAP 报告移动到 Jenkins 工作空间
                    sh "mv ./${reportFileName} ./${reportFileName}"
                }
            }
        }
        stage('Deploy') {
            // 部署到远程服务器
            steps {
                script {
                    // StrictHostKeyChecking=no 表示不检查远程主机的公钥 建议配置好ssh的免密登录
                    // Step 1: 传输文件到远程服务器 scp -v 可以查看文件传输的进度
                    sh """
                        scp -v -o StrictHostKeyChecking=no target/${JAR_FILE} ${SERVER_USER}@${SERVER_IP}:${TARGET_DIR}
                    """

                    // Step 2: 杀死已存在的进程
                    def killStatus = sh(script: """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'pgrep -f ${JAR_FILE} | xargs kill -9 || true'
                    """, returnStatus: true)

                    echo "Kill process exit status: ${killStatus}"

                    // Step 3: 启动新的进程
                    sh """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'nohup java -jar ${TARGET_DIR}/${JAR_FILE} > /dev/null 2>&1 &'
                    """
                }
            }
        }
    }

    post {
        always {
            // 每次构建结束后清理工作目录
            cleanWs()
        }
        success {
            echo 'Deployment finished successfully'
        }
        failure {
            echo 'Deployment failed'
        }
    }
}
