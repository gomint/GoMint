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
        sh 'mvn -B -DskipTests clean install'
      }
    }
    stage('Store') {
      steps {
        archiveArtifacts 'gomint-server/target/GoMint.jar'
      }
    }
  }
}
