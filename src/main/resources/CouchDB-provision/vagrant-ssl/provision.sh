#!/usr/bin/env sh

set -x
set -e

# https://docs.couchdb.org/en/latest/install/unix.html
# https://www.howtoforge.com/tutorial/how-to-install-apache-couchdb-on-centos-7/
# https://www.tecmint.com/install-apache-couchdb-on-centos-8/
# https://medium.com/@willizoe/installing-apache-couchdb-on-centos-8-a2bfb8e51a74
# https://docs.couchdb.org/en/latest/setup/single-node.html
# https://docs.couchdb.org/en/latest/setup/cluster.html
# https://downloads.apache.org/couchdb/source/
# SSL certificates (HTTPS) in CouchDB: 
# https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=48203146  
#
# A Guide to CouchDB Installation, Configuration and Monitoring
# https://www.monitis.com/blog/a-guide-to-couchdb-installation-configuration-and-monitoring/
# Last updated on 21.03.2019
# 
# https://archive.apache.org/dist/couchdb/source/1.7.2/
# https://archive.apache.org/dist/couchdb/1.2.1/
# https://archive.apache.org/dist/couchdb/releases/1.2.0/apache-couchdb-1.2.0.tar.gz
# https://archive.apache.org/dist/couchdb/notes/1.6.0/apache-couchdb-1.6.0.html

# https://www.cyberciti.biz/faq/add-create-a-sudo-user-on-centos-linux-8/



create_certificates () {
# BEGIN create_certificates
set -x
set -e
#domain=couchdb
#commonname=$domain

#Change to your company details
# country=CH ; state=Zurich ; locality=Zurich ; organization=CLOUD ; organizationalunit=iT; email=admin@cloud.ch
country=RO
state=Europe
locality=TSR
organization=DBA
organizationalunit=CLOUD
commonname=couchdb.eu
email=admin@couchdb.eu

#Optional
#password=Apache.CouchDB.3

# creating SSL certificates

#  commands that require manuaL entry for company details : 
#  sudo openssl genrsa -out /opt/couchdb/couch.key 2048
#  sudo openssl req -new -key /opt/couchdb/couch.key -out /opt/couchdb/couch.csr
#  sudo openssl x509 -req -sha256 -days 1095 -in /opt/couchdb/couch.csr -signkey /opt/couchdb/couch.key -out /opt/couchdb/couch.crt

# SSL with pass + automatic details
#sudo openssl genrsa -passout pass:$password -out /opt/couchdb/couch.key 2048
#sudo openssl req -new -key /opt/couchdb/couch.key -out /opt/couchdb/couch.csr -passin pass:$password -subj "/C=$country/ST=$state/L=$locality/O=$organization/OU=$organizationalunit/CN=$commonname/emailAddress=$email" 
#sudo openssl x509 -req -sha256 -days 1095 -in /opt/couchdb/couch.csr -signkey /opt/couchdb/couch.key -out /opt/couchdb/couch.crt

sudo openssl genrsa -out /opt/couchdb/couch.key 2048
sudo openssl req -new -key /opt/couchdb/couch.key -out /opt/couchdb/couch.csr -subj "/C=$country/ST=$state/L=$locality/O=$organization/OU=$organizationalunit/CN=$commonname/emailAddress=$email" 
sudo openssl x509 -req -sha256 -days 1095 -in /opt/couchdb/couch.csr -signkey /opt/couchdb/couch.key -out /opt/couchdb/couch.crt


# adding couchdb user to root group + change password
# In CentOS 8 Linux server all members of the wheel group have sudo access.
sudo usermod -aG wheel couchdb
# adduser -G wheel couchdb
sudo echo -e "couchdb\ncouchdb" | passwd couchdb
# https://www.systutorials.com/changing-linux-users-password-in-one-command-line/

# assign certificates to couchdb user : 
sudo chown couchdb:couchdb /opt/couchdb/couch.crt
sudo chown couchdb:couchdb /opt/couchdb/couch.key
# END create_certificates
}



restore_bulk_docs () {
# BEGIN restore_bulk_docs
cat > /opt/couchdb/hsmdidata.json << ENDJSON
{
 "docs": [
  {"id":"101e661441775c2ee3a61a6ee1000cad","key":"101e661441775c2ee3a61a6ee1000cad","value":{"rev":"1-149dca288734e89e4811449fc94d7cb9"},"doc":{"_id":"101e661441775c2ee3a61a6ee1000cad","_rev":"1-149dca288734e89e4811449fc94d7cb9","timestamp":1475651942170,"type":"RegionConfig","regionName":"EU","vHSMSlot":1,"label":"Europe"}},
  {"id":"101e661441775c2ee3a61a6ee1000cfb","key":"101e661441775c2ee3a61a6ee1000cfb","value":{"rev":"1-a49333e4c691d758e9a663990446bef0"},"doc":{"_id":"101e661441775c2ee3a61a6ee1000cfb","_rev":"1-a49333e4c691d758e9a663990446bef0","timestamp":1475651942240,"type":"RegionConfig","regionName":"ROW","vHSMSlot":2,"label":"Rest of the world"}},
  {"id":"_design/hsmdi","key":"_design/hsmdi","value":{"rev":"2-b0dda63f6721f0e197c990012734850f"},"doc":{"_id":"_design/hsmdi","_rev":"2-b0dda63f6721f0e197c990012734850f","views":{"all":{"map":"function(doc){ emit(doc._id, doc._rev)}"},"allCountryConfigs":{"map":"function(doc){ if (doc.type && doc.type == 'CountryConfig') {emit(doc._id, doc)}}"},"allRegionConfigs":{"map":"function(doc){ if (doc.type && doc.type == 'RegionConfig') {emit(doc._id, doc)}}"},"allConflicts":{"map":"function(doc) { if(doc._conflicts) { emit(doc._conflicts, null)}}"},"byRegionName":{"map":"function(doc) { if(doc.type && doc.type == 'RegionConfig' && doc.regionName) {emit(doc.regionName, doc)}}"},"byCountry":{"map":"function(doc) { if(doc.type && doc.type == 'CountryConfig' && doc.country) {emit(doc.country, doc)}}"}}}},
  {"id":"e8421a9964b88e12eda5e01fc000010f","key":"e8421a9964b88e12eda5e01fc000010f","value":{"rev":"3-ab3c7218aef96a1241bc9ae52e9b64c1"},"doc":{"_id":"e8421a9964b88e12eda5e01fc000010f","_rev":"3-ab3c7218aef96a1241bc9ae52e9b64c1","timestamp":1557476120912,"type":"CountryConfig","country":"DE","drupalURL":"http://172.16.1.33:8001/","currentRsaId":"9d47957c5504ff2b963cf97ce42c644fbac8ff6f","newRsaId":"","createCountryRunning":false,"keyRotationRunning":false,"vhsmslot":1}}
 ]
}
ENDJSON

# cd /opt/couchdb/
curl -d @/opt/couchdb/hsmdidata.json -H "Content-type: application/json" -X POST http://admin:atos@127.0.0.1:5984/hsmdi/_bulk_docs
# cd -
# END restore_bulk_docs
}

bidirectional_replication () {
# BEGIN bidirectional_replication
#curl -X POST http://admin:atos@192.168.83.86:5984/_node/couchdb@192.168.83.86/_replicate -d '{"source":"http://admin:atos-hsmdi@192.168.83.86:5984/_node/couchdb@172.16.0.90/hsmdi", "target":"http://admin:atos-hsmdi@192.168.83.89:5984/_node/couchdb@192.168.83.89/hsmdi","continuous":true}' -H "Content-Type: application/json"
#curl -X POST http://admin:atos@192.168.83.89:5984/_node/couchdb@192.168.83.89/_replicate -d '{"source":"http://admin:atos-hsmdi@192.168.83.89:5984/_node/couchdb@192.168.83.89/hsmdi", "target":"http://admin:atos-hsmdi@172.16.0.90:5984/_node/couchdb@192.168.83.86/hsmdi","continuous":true}' -H "Content-Type: application/json"
# END bidirectional_replication
}

create_cluster () {
# BEGIN create_cluster
# https://docs.couchdb.org/en/latest/setup/cluster.html
# aLL nodes : 
# curl -X POST -H "Content-Type: application/json" http://admin:atos@127.0.0.1:5984/_cluster_setup -d '{"action": "enable_cluster", "bind_address":"0.0.0.0", "username": "admin", "password":"atos", "node_count":"2"}'
# 1st node = coordination-node --- To join 1st node to the cluster, run these commands for each node you want to add:  
# curl -X POST -H "Content-Type: application/json" http://admin:password@<setup-coordination-node>:5984/_cluster_setup -d '{"action": "enable_cluster", "bind_address":"0.0.0.0", "username": "admin", "password":"atos", "port": 5984, "node_count": "3", "remote_node": "<remote-node-ip>", "remote_current_user": "<remote-node-username>", "remote_current_password": "<remote-node-password>" }'
# curl -X POST -H "Content-Type: application/json" http://admin:password@<setup-coordination-node>:5984/_cluster_setup -d '{"action": "add_node", "host":"<remote-node-ip>", "port": <remote-node-port>, "username": "admin", "password":"password"}'
# Once this is done run the following command to complete the cluster setup and add the system databases:
# curl -X POST -H "Content-Type: application/json" http://admin:password@<setup-coordination-node>:5984/_cluster_setup -d '{"action": "finish_cluster"}'
# Verify install:
# curl http://admin:password@<setup-coordination-node>:5984/_cluster_setup
# Verify all cluster nodes are connected:
# curl http://admin:password@<setup-coordination-node>:5984/_membership
#
curl -X POST -H "Content-Type: application/json" http://admin:atos@192.168.83.86:5984/_cluster_setup -d '{"action": "enable_cluster", "bind_address":"0.0.0.0", "username": "admin", "password":"atos", "node_count":"2"}'
curl -X POST -H "Content-Type: application/json" http://admin:atos@192.168.83.89:5984/_cluster_setup -d '{"action": "enable_cluster", "bind_address":"0.0.0.0", "username": "admin", "password":"atos", "node_count":"2"}'
curl -X POST -H "Content-Type: application/json" http://admin:atos@192.168.83.86:5984/_cluster_setup -d '{"action": "enable_cluster", "bind_address":"0.0.0.0", "username": "admin", "password":"atos", "port": 5984, "node_count": "2", "remote_node": "192.168.83.89", "remote_current_user": "admin", "remote_current_password": "atos" }'
curl -X POST -H "Content-Type: application/json" http://admin:atos@192.168.83.86:5984/_cluster_setup -d '{"action": "add_node", "host":"192.168.83.89", "port": "5984", "username": "admin", "password":"atos"}'
curl -X POST -H "Content-Type: application/json" http://admin:atos@192.168.83.86:5984/_cluster_setup -d '{"action": "finish_cluster"}'
# END create_cluster
}


#disable SELINUX
if test `getenforce` = 'Enforcing'; then setenforce 0; fi
sed -Ei 's/^SELINUX=.*/SELINUX=disabled/' /etc/selinux/config

#stop firewall
systemctl stop firewalld
systemctl disable firewalld
#systemctl status firewalld

# cat << END1 | sudo tee /etc/yum.repos.d/apache-couchdb.repo > /dev/null
cat > /etc/yum.repos.d/apache-couchdb.repo << END1
[bintray--apache-couchdb-rpm]
name=bintray--apache-couchdb-rpm
# baseurl=http://apache.bintray.com/couchdb-rpm/el$releasever/$basearch/
# baseurl=http://apache.bintray.com/couchdb-rpm/el7/x86_64/
baseurl=http://apache.bintray.com/couchdb-rpm/el8/x86_64/
gpgcheck=0
repo_gpgcheck=0
enabled=1
END1

dnf clean packages
# dnf -y update
dnf -y install epel-release 
# dnf makecache
dnf clean packages
dnf -y install yum-utils mc iproute net-tools curl
dnf clean packages
dnf -y --enablerepo=bintray--apache-couchdb-rpm clean metadata
# dnf clean packages
# yum clean all
dnf -y install couchdb
# dnf install -y couchdb
# yum -y remove  couchdb-3.1.1
# yum -y install couchdb-3.1.1
# yum -y install couchdb-2.3.1
dnf clean packages

# creating backup of local.ini and default.ini as initially installed 
sed -n '/^;/!p' /opt/couchdb/etc/local.ini
sed -n '/^;/!p' /opt/couchdb/etc/default.ini
# cp /opt/couchdb/etc/local.ini /opt/couchdb/etc/ini.local.ini.bak
# cp /opt/couchdb/etc/default.ini /opt/couchdb/etc/ini.default.ini.bak
# sed -n '/^;/!p' /opt/couchdb/etc/ini.local.ini.bak
# sed -n '/^;/!p' /opt/couchdb/etc/ini.default.ini.bak

# in order to ssh into VM without << vagrnt ssh hostname >> / directly with << ssh ip >> 
# sed -n '/^#/!p' /etc/ssh/sshd_config
# sed -i 's/^PasswordAuthentication.*/PasswordAuthentication yes/' /etc/ssh/sshd_config
# systemctl restart sshd.service

create_certificates

# setting in default.ini : 5984 http access on all interfaces / ips
cat /opt/couchdb/etc/default.ini | grep bind_address
cat /opt/couchdb/etc/default.ini | grep port
# cat /opt/couchdb/etc/local.ini | grep -i UUID

sed -i 's/^;bind_address =.*/bind_address = 0.0.0.0/' /opt/couchdb/etc/default.ini
sed -i 's/^;port =.*/port = 5984/' /opt/couchdb/etc/local.ini

# setting in local.ini : 5984 http access on all interfaces / ips
cat > /opt/couchdb/etc/local.ini << END2

[chttpd]
port = 5984
bind_address = 0.0.0.0

[ssl]
enable = true
cert_file = /opt/couchdb/couch.crt
key_file = /opt/couchdb/couch.key

[admins]
admin = atos

END2

cat /opt/couchdb/etc/local.ini
cat /opt/couchdb/etc/local.ini | grep bind_address
cat /opt/couchdb/etc/local.ini | grep port
cat /opt/couchdb/etc/local.ini | grep admin


# /opt/couchdb/etc/vm.args
cat /opt/couchdb/etc/vm.args | grep name






echo $1
if [ $1 == "define-first" ]
then
    # 192.168.83.86
	cat /opt/couchdb/etc/vm.args | grep name 
	sed -i 's/^-name.*/-name couchdb@192.168.83.86/' /opt/couchdb/etc/vm.args
echo " entered if : replication define-first "
else
echo " entered else : replication define-second "
    # 192.168.83.89
	sed -i 's/^-name.*/-name couchdb@192.168.83.89/' /opt/couchdb/etc/vm.args
fi
	echo "-kernel inet_dist_listen_min 9100" >>      /opt/couchdb/etc/vm.args
	echo "-kernel inet_dist_listen_max 9200" >>      /opt/couchdb/etc/vm.args




# systemctl start couchdb
# systemctl enable couchdb
systemctl enable --now couchdb.service
systemctl status couchdb
netstat -plntu
ps -fu couchdb --forest
# systemctl restart couchdb

#firewall-cmd --add-port=5984/tcp --permanent
#firewall-cmd --reload

sleep 5

curl http://admin:atos@localhost:5984
curl -X PUT http:///admin:atos@localhost:5984/_config/admins/atos -d '"atos-hsmdi"'
curl -X PUT http://admin:atos@localhost:5984/_users
curl -X PUT http://admin:atos@127.0.0.1:5984/_replicator
curl -X PUT http://admin:atos@localhost:5984/hsmdi
curl -X PUT http://admin:atos@localhost:5984/jnosqlfilescanner
curl -X GET http://admin:atos@localhost:5984/_all_dbs
curl -X GET http://admin:atos@localhost:5984/_membership

curl -i -k -X GET https://admin:atos@localhost:6984
# curl -k -X GET https://admin:atos@192.168.83.86:6984
# curl -k -X GET https://admin:atos@192.168.83.89:6984
#  curl -k -X GET https://atos:atos-hsmdi@localhost:6984/_all_dbs
#  curl -k -X GET https://admin:atos@localhost:6984/_membership
#  curl --noproxy "*" -i -k -X GET http://admin:atos@192.168.83.86:5984/_node/couchdb@192.168.83.86/_all_dbs
#  curl --noproxy "*" -i -k -X GET http://admin:atos@192.168.83.86:5984/_node/couchdb@192.168.83.86/_stats

create_cluster
restore_bulk_docs

echo "Complete"


# url -X GET http://admin:atos@localhost:5984/_users/_all_docs?include_docs=true
# less /var/log/couchdb/couchdb.log
# tail /var/log/couchdb/couchdb.log
# tail -f /var/log/couchdb/couchdb.log
 
# https://docs.couchdb.org/en/latest/setup/cluster.html
#
# Create admin user and password:
# curl -s -X PUT http://admin:atos@localhost:5984/_node/_local/_config/admins/atos -d '"atos-hsmdi"'
# curl -s -X PUT http://admin:atos@localhost:5984/_node/_local/_config/admins/user -d '"passwd"'
#
# curl -X PUT http://admin:atos@localhost:5984/jnosqlfilescanner
# 
# bind the clustered interface to all IP addresses availble on this machine
# curl -X PUT http://admin:atos@localhost:5984/_node/_local/_config/chttpd/bind_address -d '"0.0.0.0"'
# 
# get two UUIDs to use later on setup. Be sure to use the SAME UUIDs on all nodes.
# curl http://admin:atos@localhost:5984/_uuids?count=2 
# # result # {"uuids":["FIRST-UUID-GOES-HERE","SECOND-UUID-GOES-HERE"]}
# # result # {"uuids":["53b5aba89eadcac2d6d60b6c190000eb","53b5aba89eadcac2d6d60b6c19000308"]}
# If not using the setup wizard / API endpoint, the following 2 steps are required:
# Set the UUID of the node to the first UUID you previously obtained:
# curl -X PUT http://admin:atos@localhost:5984/_node/_local/_config/couchdb/uuid -d '"FIRST-UUID-GOES-HERE"'
#
# Set the shared http secret for cookie creation to the second UUID:
# curl -X PUT http://admin:atos@localhost:5984/_node/_local/_config/couch_httpd_auth/secret -d '"SECOND-UUID-GOES-HERE"'
#
# Access Apache CouchDB from a web browser
# http://<your-server-ip-address>:5984/_utils/
# http://admin:atos@192.168.83.86:5984/_utils/
# http://admin:atos@192.168.83.89:5984/_utils/
# 
# Access Apache CouchDB version from Linux terminal 
# curl http://admin:atos@192.168.83.89:5984
# curl http://admin:atos@192.168.83.86:5984
# curl -X GET http://admin:atos@192.168.83.89:5984/_users/_all_docs?include_docs=true
#
#   curl -X GET http://admin:atos@192.168.83.89:5984/_all_dbs
#   curl -X GET https://admin:atos@192.168.83.89:5984/_all_dbs
# 

#delete_localhost_node_from_cluster () {
# BEGIN delete_node_from_cluster
# https://docs.couchdb.org/en/latest/cluster/nodes.html#removing-a-node
# curl -X GET http://admin:atos-hsmdi@localhost:5984/_membership
# curl -X GET http://admin:atos-hsmdi@172.16.0.90:5984/_node/_local/_nodes/couchdb@127.0.0.1
### response ### {"_id":"couchdb@127.0.0.1","_rev":"1-967a00dff5e02add41819138abb3284d"}
# curl -X DELETE http://admin:atos-hsmdi@172.16.0.90:5984/_node/_local/_nodes/couchdb@127.0.0.1?rev=1-967a00dff5e02add41819138abb3284d
# curl -X GET http://admin:atos-hsmdi@localhost:5984/_membership
# END delete_node_from_cluster
#}
