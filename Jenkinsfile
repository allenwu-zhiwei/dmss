pipeline {
    agent any

    environment {
        SERVER_IP = 'do001-why-ubuntu'        		// server ip
        SERVER_USER = 'root'    					// server username
        TARGET_DIR = '/opt/module'                  // server target directory
        JAR_FILE = 'dmss.jar'                // jar file name
        ZAP_DOCKER_IMAGE = 'zaproxy/zap-stable' // ZAP Docker image
        ZAP_PORT = '8081'                        // ZAP listening port
        TARGET_URL = 'http://128.199.224.162:8080'    // ZAP scanning URL
        SONARQUBE_SERVER = 'SonarCloud' // Name of the SonarCloud server in Jenkins
        SONAR_PROJECT_KEY = 'WUHAOYI_dmss' // Your SonarCloud project key
        SONAR_ORG = 'wuhaoyi' // Your SonarCloud organization key
        SONAR_TOKEN = credentials('Sonarcloud-Credential') // Jenkins credential storing SonarCloud token
    }

    stages {
        stage('Clone Source Code') {
            steps {
                // checkout code from git
                git branch: 'master', url: 'https://github.com/allenwu-zhiwei/dmss.git'
            }
        }

        stage('Build Project') {
            steps {
                ansiColor('xterm')
                    {
                        // build project
                        sh 'mvn clean package -DskipTests'
                    }
            }
        }

        stage('Verify Build Output') {
            // Verify the build output
            steps {
                sh 'ls -l target/'
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                withSonarQubeEnv(SONARQUBE_SERVER) {
                    // Run SonarCloud analysis using Maven
                    sh """
                    mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.organization=${SONAR_ORG} \
                        -Dsonar.login=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Test') {
            steps {
                ansiColor('xterm') {
                      // run test
                      sh 'mvn test'
                }
            }
        }
        stage('OWASP ZAP Scan') {
            steps {
                script {
                    // define a timestamp for the report file
                    def timestamp = new Date().format("yyyyMMdd_HHmmss")
                    def reportFileName = "zap_report_${timestamp}.html"

                    // run ZAP scan in a Docker container
                    def zapContainerId = sh(script: """
                        docker run -d -u root -p ${ZAP_PORT}:${ZAP_PORT} -v \$(pwd):/zap/wrk zaproxy/zap-stable zap.sh -cmd -quickurl ${TARGET_URL} -quickout /zap/wrk/${reportFileName}
                    """, returnStdout: true).trim()

                    // wait for the scan to finish
                    sleep(time: 30, unit: 'SECONDS')

                    // copy the report file from the container to the target directory
                    sh """
                        docker cp ${zapContainerId}:/zap/wrk/${reportFileName} /opt/files/zap/
                    """

                    // stop and remove the container
                    sh "docker rm -f ${zapContainerId}"
                }
            }
        }

        stage('Deploy') {
            // Deploy the application to the server
            steps {
                script {
                    // Step 1: upload jar file to server ( scp -v can show more details)
                    sh """
                        scp -v -o StrictHostKeyChecking=no target/${JAR_FILE} ${SERVER_USER}@${SERVER_IP}:${TARGET_DIR}
                    """

                    // Step 2: kill the existing process
                    def killStatus = sh(script: """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'pgrep -f ${JAR_FILE} | xargs kill -9 || true'
                    """, returnStatus: true)

                    echo "Kill process exit status: ${killStatus}"

                    // Step 3: start the new process
                    sh """
                        ssh -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} 'nohup java -jar ${TARGET_DIR}/${JAR_FILE} > /dev/null 2>&1 &'
                    """
                }
            }
        }
    }

    post {
        always {
            // clean workspace
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
