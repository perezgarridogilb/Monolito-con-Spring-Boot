# commands
macbook@MacBooks-MacBook-Pro ~/D/S/M/products-command (master) [1]> docker run -d --name kafka -p 9092:9092 --pull=always apache/kafka:latest

docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --create --topic products.commands \
  --partitions 1 --replication-factor 1

docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic products.replies --partitions 1 --replication-factor 1  


macbook@MacBooks-MacBook-Pro ~/D/S/M/products-command (master) [1]> docker exec -it kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

## Kubernetes example
kubectl run kbillingapp --image=sotobotero/udemy-devops:0.0.1 --port=80 80
kubectl describe pod kbillingapp