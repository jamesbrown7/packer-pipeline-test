node {
   def packerHome, packerTemplateName
   stage('Preparation') { // for display purposes
      // ** NOTE: This 'Packer' tool must be configured
      // **       in the global configuration.           
      packerHome = '/usr/local/bin'
      packerTemplateName = "packer.json"
   }
   stage('Packer (AMI generation)') {
      // Run the maven build
      if (isUnix()) {
         sh "pwd; ls -R"
         sh "'${packerHome}/packer' build pipe/${packerTemplateName}"
      } else {
         bat(/"${packerHome}\packer" build pipe\${packerTemplateName}/)
      }
   }
}
