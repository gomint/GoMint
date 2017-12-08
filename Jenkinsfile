pipeline {
  agent {
    docker {
      image 'maven:3'
      args '-v /root/.m2:/root/.m2'
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
        sh 'mvn -B -DskipTests clean install'
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
      discordSend description: 'GoMint Build has been succeeded: ${currentBuild.absoluteUrl}artifact/gomint-server/target/GoMint.jar', footer: 'Provided with <3', link: currentBuild.absoluteUrl, successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), title: JOB_NAME, webhookURL: 'https://discordapp.com/api/webhooks/384326195866763274/4oqtJEmf_UDcylRq7R1TUMGoSTO_U5lSwItCkssgrQBqHtNYySt-Wmxc9cme-JdOCwsB'
    }

    failure {
      discordSend description: 'GoMint Build failed', footer: 'Provided with <3', link: currentBuild.absoluteUrl, successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), title: JOB_NAME, webhookURL: 'https://discordapp.com/api/webhooks/384326195866763274/4oqtJEmf_UDcylRq7R1TUMGoSTO_U5lSwItCkssgrQBqHtNYySt-Wmxc9cme-JdOCwsB'
    }
  }
}
