# Packer Pipeline Test

## Linux
The Linux packer pipeline test uses two files:
* Jenkinsfile - contains groovy code that defines the pipeline for Jenkins
* packer.json - contains a Hashicorp Packer template to build a demo Linux AMI

### Preparing your base AMI
No special steps are required to prepare your base AMI. So long as Packer can
login to it via SSH, any Linux AMI should work fine.

### How It Works
The sequence of operation for the Linux packer pipeline is as follows:
1. Jenkins checks out this git repo
1. The Jenkins build job specifies the file to use to define the pipeline (Jenkinsfile)
1. Jenkins executes the groovy in the Jenkinsfile, which calls Packer, specifying
the Linux packer template, packer.json.
1. Packer starts a new EC2 instance using the current Jenkins AWS credentials,
which is based on the AMI specified within builders.source_ami.
1. Once the instance has started and is accepting connections, Packer SSHes to the
EC2 instance, and runs the commands in provisioners.inline.
1. Once complete, the EC2 instance is snapshotted, and the new AMI named as specified
in builders.ami_name.

## Windows
Windows is more complex, because of the method Packer must use to control the
Windows AMI build host, namely: WinRM (Windows Remote Management).

This method used is based on the article at: http://blog.petegoo.com/2016/05/10/packer-aws-windows

### How It Works
The sequence of operation for the Windows packer pipeline is as follows:
1. Jenkins checks out this git repo
1. The Jenkins build job specifies the file to use to define the pipeline (Jenkinsfile-win)
1. Jenkins executes the groovy in the Jenkinsfile, which calls Packer, specifying
the Windows packer template, windows-packer.json.
1. Packer starts a new EC2 instance using the current Jenkins AWS credentials,
which is based on the AMI specified within builders.source_ami. It will automatically
include the ec2-userdata.ps1 file in the new EC2 instance's user data.
1. Once the instance has started and is accepting connections, Packer uses its
WinRM connection on port 5986 to the host it just started. It will require you
set the following fields within builders:
```
"user_data_file":"./ec2-userdata.ps1",
"communicator": "winrm",
"winrm_username": "Administrator",
"winrm_use_ssl": true,
"winrm_insecure": true
```
1. Packer runs the commands in provisioners.inline.
1. Once complete, the EC2 instance is snapshotted, and the new AMI named as specified
in builders.ami_name.
