kill -9 $(netstat -nlp | grep :443 | awk '{print $7}' | awk -F"/" '{ print $1 }')
sudo rm -rf /var/webapp