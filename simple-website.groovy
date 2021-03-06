env.node_ip='167.172.146.135'
node{
    // properties([
    //     parameters([
    //         string(defaultValue: '', description: 'Provide Remote Node IP', name: 'node_ip', trim: true)
    //     ])
    // ])


    stage("Git Clone"){
        git url: 'https://github.com/shamal112mn/jenkins-pipeline.git'
    }

    withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-ssh-key', keyFileVariable: 'SSH_KEY', passphraseVariable: '', usernameVariable: 'SSH_USERNAME')]) {
        stage('Install Apache'){
            sh "ssh -o StrictHostKeyChecking=false -i $SSH_KEY root@${node_ip} yum install httpd -y"
        }
        stage('Move Files'){
            sh "scp -r -i $SSH_KEY * root@${node_ip}:/var/www/html/"
        }
        stage('Start Apache'){
            sh "ssh -o StrictHostKeyChecking=false -i $SSH_KEY root@${node_ip} 'systemctl start httpd && systemctl enable httpd'"
        }
        stage('Final stage'){
            sh "echo End ver 3"
        }
    } 
}

