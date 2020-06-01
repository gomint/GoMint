pipeline {
  agent {
    docker {
      image 'maven:3.6-jdk-11'
      args '-v /root/.m2/:/root/.m2/'
    }
    
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B clean install'
      }
    }
    stage('Store') {
      steps {
        archiveArtifacts 'target/jraknet-*.jar'
      }
    }
  }
}