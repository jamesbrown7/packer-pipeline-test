node {
   def packerHome, packerTemplateName
   stage('Preparation') { // for display purposes
      // Get the packer base config from S3
      git 'https://github.com/jglick/simple-maven-project-with-tests.git'

      // ** NOTE: This 'Packer' tool must be configured
      // **       in the global configuration.           
      packerHome = tool 'Packer'
      packerTemplateName = "packer.json"
   }
   stage('Packer (AMI generation)') {
      // Run the maven build
      if (isUnix()) {
         sh "'${packerHome}/packer' build ${packerTemplateName}"
      } else {
         bat(/"${packerHome}\packer" build ${packerTemplateName}/)
      }
   }
}
