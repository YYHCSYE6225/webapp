sudo cp /var/webapp/scripts/cloudwatch-config.json /opt/
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/cloudwatch-config.json \
    -s
sudo nohup node /home/ubuntu/statsd/stats.js /home/ubuntu/statsd/config.js > /dev/null 2> /dev/null < /dev/null &