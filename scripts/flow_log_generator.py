import random
import time

ip_list = [
    '192.168.1.1',
    '192.168.1.2',
    '10.0.0.1',
    '172.16.0.1',
    '8.8.8.8',
    '17.23.55.11',
    '193.168.1.1',
    '194.168.1.2',
    '11.0.0.1',
    '173.16.0.1',
    '9.8.8.8',
    '19.23.55.11',
]

account_id = '123456789012'
interface_id = 'eni-abcdef12'
op_filename = 'flowlog.log'

def generate_flow_log():
    version = 2
    src_ip = random.choice(ip_list)
    dst_ip = random.choice(ip_list)
    while dst_ip == src_ip:
        dst_ip = random.choice(ip_list)

    protocol = random.choice([0, 20])
    src_port = random.randint(1, 2000)
    dst_port = random.randint(1, 2000)
    packets = random.randint(1, 10)
    bytes_transferred = packets * random.randint(40, 1500)

    start_time = int(time.time())
    end_time = start_time + random.randint(1, 10)

    action = random.choice(['ACCEPT', 'REJECT'])
    log_status = 'OK'

    flow_log = f'{version} {account_id} {interface_id} {src_ip} {dst_ip} {src_port} {dst_port} {protocol} {packets} {bytes_transferred} {start_time} {end_time} {action} {log_status}\n'

    with open(op_filename, 'a') as log_file:
        log_file.write(flow_log)

def generate_flow_logs():
    for _ in range(100000):
        generate_flow_log()

generate_flow_logs()