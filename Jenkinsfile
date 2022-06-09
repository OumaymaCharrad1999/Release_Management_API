
properties([parameters([
    choice(choices: ['Next', 'Deploy'], name: 'choice'),
    string(defaultValue: '', name: 'version_to_deploy', trim: false)
])])

node {

    def response 
    def release
    def pom
    def status
    
    if (choice == 'Next') {   

        status = httpRequest acceptType: 'APPLICATION_JSON', 
            contentType: 'APPLICATION_JSON',
            httpMode: 'GET',
            consoleLogResponseBody: true,
            url: 'http://localhost:8081/release-management-api/get-status/Project2'
        println "Status: "+ status.content

        if (status.content!='Branched' && status.content!='Build' && status.content!='Checked') {

            stage('Get next version of the project') { 
 
                response = httpRequest acceptType: 'APPLICATION_JSON', 
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'GET',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/plan-release-api/Project2'
                release =  response.content
                println "Version to build: "+ release 

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Created'

            }
    
            stage('Create Branch in Git using the returned version') { 

                bat "git clone https://github.com/Oumayma-Charrad/Release_Management_API.git C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release"
                pom = readMavenPom file: "C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release\\pom.xml"
                powershell returnStdout: true, script: "(Get-Content C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release\\pom.xml).replace('${pom.version}', '$release') | Out-File C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release\\pom.xml"
                bat "cd C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release & git commit -am \"Change version to $release\""
                withCredentials([usernamePassword(credentialsId: '1888f01e-b82f-489a-9755-92f68a2645e1',
                    passwordVariable: 'GIT_PASSWORD',
                    usernameVariable: 'GIT_USERNAME')]) {
                        bat "git config --global credential.helper wincred"
                        bat "cd C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release & git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USERNAME}/Release_Management_API.git"
                        bat "cd C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release & git checkout -b $release"
                        bat "cd C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release & git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USERNAME}/Release_Management_API.git"
                }

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Branched'

            }
    
            stage('Get sources from Git Branch') { 

                git branch: "$release", url: 'https://github.com/Oumayma-Charrad/Release_Management_API.git'

            }
   
            stage('Build') { 

                withMaven(jdk: 'jdk1.8.0', maven: 'mvn3.6.3', mavenLocalRepo: 'C:\\Users\\charr\\.m2\\repository') {
                    bat "mvn clean package"
                }

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Build'

            }
    
            stage('Nexus Repository') {
   
                pom = readMavenPom file: "C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release\\pom.xml"
                nexusPublisher nexusInstanceId: 'localNexus',
                    nexusRepositoryId: 'releases',
                    packages: [[$class: 'MavenPackage',
                    mavenAssetList: [[classifier: '',
                    extension: '',
                    filePath: "C:\\Windows\\System32\\config\\systemprofile\\AppData\\Local\\Jenkins\\.jenkins\\workspace\\6. Release Management API\\target\\${pom.artifactId}-${pom.version}.${pom.packaging}"]],
                    mavenCoordinate: [artifactId: "${pom.artifactId}",
                    groupId: "${pom.groupId}",
                    packaging: "${pom.packaging}",
                    version: "${pom.version}"]]]

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Checked'

            }
    
            stage('Deploy') { 
 
                bat ''' net stop tomcat8
                    net start tomcat8 '''
                def (groupe, id) = "${pom.groupId}".tokenize( '.' )
                httpRequest outputFile: "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\${pom.artifactId}.${pom.packaging}",
                    responseHandle: 'NONE',
                    url: "http://localhost:8085/nexus/service/local/repositories/releases/content/$groupe/$id/${pom.artifactId}/${pom.version}/${pom.artifactId}-${pom.version}.${pom.packaging}",
                    wrapAsMultipart: false

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Installed'

            }
    
        }
    
        else {

            stage('Get sources from Git Branch') { 

                response = httpRequest acceptType: 'APPLICATION_JSON', 
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'GET',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/plan-release-api/Project2'
                release =  response.content
                println "Version to build: "+ release

                git branch: "$release", url: 'https://github.com/Oumayma-Charrad/Release_Management_API.git'

            }
   
            stage('Build') { 

                withMaven(jdk: 'jdk1.8.0', maven: 'mvn3.6.3', mavenLocalRepo: 'C:\\Users\\charr\\.m2\\repository') {
                    bat "mvn clean package"
                }

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Build'

            }
    
            stage('Nexus Repository') {

                pom = readMavenPom file: "C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\$release\\pom.xml"
    
                nexusPublisher nexusInstanceId: 'localNexus',
                    nexusRepositoryId: 'releases',
                    packages: [[$class: 'MavenPackage',
                    mavenAssetList: [[classifier: '',
                    extension: '',
                    filePath: "C:\\Windows\\System32\\config\\systemprofile\\AppData\\Local\\Jenkins\\.jenkins\\workspace\\6. Release Management API\\target\\${pom.artifactId}-${pom.version}.${pom.packaging}"]],
                    mavenCoordinate: [artifactId: "${pom.artifactId}",
                    groupId: "${pom.groupId}",
                    packaging: "${pom.packaging}",
                    version: "${pom.version}"]]]

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Checked'

            }
    
            stage('Deploy') { 
 
                bat ''' net stop tomcat8
                    net start tomcat8 '''
                def (groupe, id) = "${pom.groupId}".tokenize( '.' )
                httpRequest outputFile: "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\${pom.artifactId}.${pom.packaging}",
                    responseHandle: 'NONE',
                    url: "http://localhost:8085/nexus/service/local/repositories/releases/content/$groupe/$id/${pom.artifactId}/${pom.version}/${pom.artifactId}-${pom.version}.${pom.packaging}",
                    wrapAsMultipart: false

                httpRequest acceptType: 'APPLICATION_JSON',
                    contentType: 'APPLICATION_JSON',
                    httpMode: 'PATCH',
                    consoleLogResponseBody: true,
                    url: 'http://localhost:8081/release-management-api/Project2/Installed'

            }   
    
        }
    
    }

    if (choice == 'Deploy') {

        stage ('Deploy') { 

            bat ''' net stop tomcat8
                net start tomcat8 '''

            pom = readMavenPom file: "C:\\Users\\charr\\OneDrive\\Bureau\\Release_Management_API\\${params.version_to_deploy}\\pom.xml"
    
            def (groupe, id) = "${pom.groupId}".tokenize( '.' )
            httpRequest outputFile: "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\${pom.artifactId}.${pom.packaging}",
                responseHandle: 'NONE',
                url: "http://localhost:8085/nexus/service/local/repositories/releases/content/$groupe/$id/${pom.artifactId}/${pom.version}/${pom.artifactId}-${pom.version}.${pom.packaging}",
                wrapAsMultipart: false

        }

    }

}

