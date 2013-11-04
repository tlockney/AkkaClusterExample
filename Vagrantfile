# -*- mode: ruby -*-

# (so far) mildly customized version of the Docker Vagrant config

BOX_NAME = ENV['BOX_NAME'] || "ubuntu"
BOX_URI = ENV['BOX_URI'] || "http://files.vagrantup.com/precise64.box"

Vagrant::Config.run do |config|
  config.vm.box = BOX_NAME
  config.vm.box_url = BOX_URI

  config.ssh.forward_agent = true

  # Provision docker and new kernel if deployment was not done.
  # It is assumed Vagrant can successfully launch the provider instance.
  if Dir.glob("#{File.dirname(__FILE__)}/.vagrant/machines/default/*/id").empty?

    pkg_cmd = "apt-get update -qq; " \
      "apt-get install -q -y build-essential openjdk-7-jdk git curl unzip tree lintian linux-headers-`uname -r` dkms; " \
      "wget -q http://repo.scala-sbt.org/scalasbt/sbt-native-packages/org/scala-sbt/sbt/0.13.0/sbt.deb; "\
      "dpkg -i sbt.deb; " \
      "echo 'Downloading VBox Guest Additions...'; " \
      "wget -q http://dlc.sun.com.edgesuite.net/virtualbox/4.2.12/VBoxGuestAdditions_4.2.12.iso; " \
      "echo -e 'mount -o loop,ro /home/vagrant/VBoxGuestAdditions_4.2.12.iso /mnt\n" \
      "echo yes | /mnt/VBoxLinuxAdditions.run\numount /mnt\n' > /root/guest_additions.sh; " \
      "chmod 700 /root/guest_additions.sh; " \
      "sed -i -E 's#^exit 0#[ -x /root/guest_additions.sh ] \\&\\& /root/guest_additions.sh#' /etc/rc.local; " \
      "echo 'Installation of VBox Guest Additions is proceeding in the background.'; " \
      "echo '\"vagrant reload\" can be used in about 2 minutes to activate the new guest additions.'; "  \
      "echo -n 'Finished at'; date"
    config.vm.provision :shell, :inline => pkg_cmd
  end
end

Vagrant::VERSION >= "1.1.0" and Vagrant.configure("2") do |config|
  config.vm.provider :virtualbox do |vb|
    config.vm.box = BOX_NAME
    config.vm.box_url = BOX_URI
    vb.customize ["modifyvm", :id, "--natdnshostresolver1", "on"]
    vb.customize ["modifyvm", :id, "--natdnsproxy1", "on"]
    vb.customize ["modifyvm", :id, "--memory", "1024"]
  end
end

