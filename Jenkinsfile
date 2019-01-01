def name = "GoMint";
def changesString = "";
pipeline {
  agent {
    docker {
      image 'maven:3.6-jdk-11'
      args '-v /root/.m2:/root/.m2 -u root'
    }
  }
  stages {
    stage('Depends') {
      steps {
        sh 'apt-get update'
        sh 'apt-get install -y openjfx git'
        script {
          def changeLogSets = currentBuild.changeSets
          def changes = [];
          for (int i = 0; i < changeLogSets.size(); i++) {
              def entries = changeLogSets[i].items
              for (int j = 0; j < entries.length; j++) {
                  def entry = entries[j]
                  changes.add("\"${entry.msg}\" by ${entry.author}")
              }
          }
          changesString = changes.join("\n")
        }
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
        archiveArtifacts 'gomint-converter/target/Converter.jar'
      }
    }

    stage ('Deploy with operation') {
      steps {
        build job: 'Operation/master', parameters: [[$class: 'StringParameterValue', name: 'GOMINT_BUILD_NUMBER', value: currentBuild.id]]
      }
    }
  }
  post {
    success {
      withCredentials([string(credentialsId: 'sentry-deploy', variable: 'sentryDeployToken')]) {
        sh """
            shortCommit=`git log -n 1 --pretty=format:'%h'`

            curl http://report.gomint.io/api/hooks/release/builtin/2/${sentryDeployToken}/ \
              -X POST \
              -H 'Content-Type: application/json' \
              -d '{"version": "\$shortCommit"}'
        """
      }

      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "${name} Build succeeded.\n\nChange(s):\n${changesString}\n\n${currentBuild.absoluteUrl}artifact/gomint-server/target/${name}.jar"
      }
    }
    failure {
      withCredentials([string(credentialsId: 'discord', variable: 'webhookUrl')]) {
        discordSend title: "#${currentBuild.id} ${JOB_NAME}", link: currentBuild.absoluteUrl, footer: "Provided with <3", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), webhookURL: "${webhookUrl}", description: "${name} Build failed.\n\nChange(s):\n${changesString}"
      }
    }
  }
}
