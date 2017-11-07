node {
   def packerHome, packerTemplateName
   stage('Preparation') { // for display purposes
      git credentialsId: 'ab48e83d-f30c-41b2-8dc5-946961f7ee72', url: 'http://fss-build10nb.gwl.com/jmsbrw/packer-cl287.git'

      // ** NOTE: This 'Packer' tool must be configured
      // **       in the global configuration.
      packerHome = '/usr/local/bin'
      packerTemplateName = "packer.json"
   }
   stage('Packer (AMI generation)') {
      // Run the maven build
      if (isUnix()) {
         sh "pwd; ls -R"
         sh "'${packerHome}/packer' build ${packerTemplateName}"
      } else {
         bat(/"${packerHome}\packer" build ${packerTemplateName}/)
      }
   }
}
