pipeline {
  agent {
    docker {
      image 'maven:3'
      args '-v /root/.m2:/root/.m2 -u root'
    }
  }
  stages {
    stage('Depends') {
      steps {
        sh 'apt-get update'
        sh 'apt-get install -y openjfx'
      }
    }
    stage('Build') {
      steps {
        echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL}"
        sh 'mvn -U -B -DskipTests clean install'
      }
    }
    stage('Store') {
      steps {
        archiveArtifacts 'gomint-server/target/GoMint.jar'
      }
    }
  }
  post {
    success {
      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "GoMint Build succeeded.\n${currentBuild.absoluteUrl}artifact/gomint-server/target/GoMint.jar"
      }
    }
    failure {
      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "GoMint Build failed."
      }
    }
  }
}