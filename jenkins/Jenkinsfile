pipeline {
    // 【agent】:指令告诉Jenkins在哪里以及如何执行Pipeline或者Pipeline子集。 所有的Pipeline都需要 agent 指令
    // 【agent none】:当在pipeline块的顶层应用时，将不会为整个Pipeline运行分配全局代理，并且每个stage部分将需要包含其自己的agent部分
	agent any
    parameters {
        string(name: 'APP_NAME', defaultValue: 'demo-aop-log', description: '应用名称[也作为运行容器别名]')
        string(name: 'DOCKER_IMAGE_REPOSITORY_PREFIX', defaultValue: 'linrx1', description: 'docker镜像仓库前缀')
        string(name: 'DOCKER_IMAGE_TAG', defaultValue: '1.0', description: 'docker镜像TAG【版本】')
    }
    environment {
        DOCKER_IMAGE_NAME = "${params.DOCKER_IMAGE_REPOSITORY_PREFIX}/${params.APP_NAME}:${DOCKER_IMAGE_TAG}"
    }
    stages {
        stage('Example') {
            steps {
                echo "默认系统参数:"
                //sh("echo job ${JOB_NAME}")
                echo "JOB_NAME: ${JOB_NAME}"
                echo "JENKINS_HOME: ${JENKINS_HOME}"
                echo "WORKSPACE: ${WORKSPACE}"
                echo "自定义参数:"
                echo "APP_NAME: ${APP_NAME}"
                echo "DOCKER_IMAGE_REPOSITORY_PREFIX: ${DOCKER_IMAGE_REPOSITORY_PREFIX}"
                echo "DOCKER_IMAGE_TAG: ${DOCKER_IMAGE_TAG}"
                echo "DOCKER_IMAGE_NAME: ${DOCKER_IMAGE_NAME}"
          }
        }
        stage('Build') {
            // 【agent docker】 用给定的容器执行Pipeline，或stage，将被动态地提供一个预先配置成基于Docker-based Pipelines的节点
            agent {
                // Docker中本地运行相互独立的Jenkins和Maven容器
                // Maven容器成为了Jenkins用来运行你的流水线项目的 agent。 这个容器寿命很短——它的寿命只是你的流水线的执行时间
                docker {
                    // agent docker iamge: 下载  maven:3-apline Docker镜像
                    image 'maven:3-alpine'
                    // agent docker还可以接受一个args参数，可以直接将参数传递给docker run命令
                    // 暂时部署的Maven Docker容器的 /root/.m2 （即Maven仓库）目录 和Docker主机文件系统的对应目录之间创建了一个相互映射,避免Maven反复下载相同依赖
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Docker-build') {
            // 在任何可用的代理上执行Pipeline或stage
 			agent any
            steps {
                echo 'docker-building..'
                sh 'chmod +x ./jenkins/scripts/docker-build.sh'
				sh './jenkins/scripts/docker-build.sh'
				sh 'docker images'
				echo 'Build Success'
            }
        }
        stage('Deploy-Production') {
            // 在任何可用的代理上执行Pipeline或stage
            agent any
            steps {
                echo 'Deploying..'
                input 'Finished using the web site? (Click "Proceed" to continue)'
                sh 'chmod +x ./jenkins/scripts/docker-run.sh'
                // 通常在阶段之间，特别是不同环境阶段之间，您可能需要人工确认是否可以继续运行。 例如，判断应用程序是否在一个足够好的状态可以进入到生产环境阶段。 这可以使用 input 步骤完成
                sh './jenkins/scripts/docker-run.sh'
                echo 'Deploy Success'
            }
        }
    }
}