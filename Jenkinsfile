pipeline {
  agent {
    docker {
      image 'maven:alpine'
      args '-v /root/.m2:/root/.m2 -u root'
    }
    
  }
  stages {
    stage('Depends') {
      steps {
        sh 'apk --no-cache add ca-certificates wget'
        sh 'wget --quiet --output-document=/etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub'
        sh 'wget https://github.com/sgerrand/alpine-pkg-java-openjfx/releases/download/8.151.12-r0/java-openjfx-8.151.12-r0.apk'
        sh 'apk add --no-cache java-openjfx-8.151.12-r0.apk'
      }
    }
    stage('Build') {
      steps {
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
      discordSend description: 'GoMint Build has been succeeded: ${currentBuild.absoluteUrl}artifact/gomint-server/target/GoMint.jar', footer: 'Provided with <3', link: currentBuild.absoluteUrl, successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), title: JOB_NAME, webhookURL: 'https://discordapp.com/api/webhooks/384326195866763274/4oqtJEmf_UDcylRq7R1TUMGoSTO_U5lSwItCkssgrQBqHtNYySt-Wmxc9cme-JdOCwsB'
    }

    failure {
      discordSend description: 'GoMint Build failed', footer: 'Provided with <3', link: currentBuild.absoluteUrl, successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), title: JOB_NAME, webhookURL: 'https://discordapp.com/api/webhooks/384326195866763274/4oqtJEmf_UDcylRq7R1TUMGoSTO_U5lSwItCkssgrQBqHtNYySt-Wmxc9cme-JdOCwsB'
    }
  }
}