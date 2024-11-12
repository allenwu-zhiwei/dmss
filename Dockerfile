# 使用 OpenJDK 17 官方镜像作为基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录为 /app
WORKDIR /app

# 将本地的 dmss.jar 文件复制到容器的 /app 目录中
COPY target/dmss.jar /app/dmss.jar

# 设置容器启动时的默认命令，运行 dmss.jar 文件
CMD ["java", "-jar", "dmss.jar"]
