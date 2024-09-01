import csv
import random

num_entries = 50000
port_min = 1
port_max = 2000

protocols = [
    "hopopt", "icmp", "igmp", "ggp", "ipv4", "st", "tcp", "cbt", "egp", "igp",
    "bbn-rcc-mon", "nvp-ii", "pup", "argus", "emcon", "xnet", "chaos", "udp",
    "mux", "dcn-meas", "hmp"
]

protocols = protocols + [protocol.upper() for protocol in protocols]

tags = [
    'tag1', 'tag2', 'tag3', 'tag4', 'tag5', 'tag6', 'tag7', 'tag8', 'tag9', 'tag10',
    'tag11', 'tag12', 'tag13', 'tag14', 'tag15', 'tag16', 'tag17', 'tag18', 'tag19', 'tag20'
]

op_filename = "lookup.csv"

existing_entries = set()

with open(op_filename, mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['dstport', 'protocol', 'tag'])
    while len(existing_entries) < num_entries:
        port_number = random.randint(port_min, port_max)
        protocol = random.choice(protocols)
        tag = random.choice(tags)
        entry = (port_number, protocol.lower(), tag.lower())
        if entry not in existing_entries:
            existing_entries.add(entry)
            writer.writerow([port_number, protocol, tag])
