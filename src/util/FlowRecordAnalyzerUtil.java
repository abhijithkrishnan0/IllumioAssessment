package util;

import exception.InvalidProtocolException;
import model.LookupCsvKey;
import model.PortProtocol;
import model.Tag;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static constants.CsvConstant.PORT_PROTO_COUNT_HEADER;
import static constants.CsvConstant.TAG_COUNT_HEADER;

public class FlowRecordAnalyzerUtil {

    public static final Map<String, Integer> PROTOCOL_NAME_TO_ID_MAP = Map.ofEntries(
            Map.entry("hopopt", 0),
            Map.entry("icmp", 1),
            Map.entry("igmp", 2),
            Map.entry("ggp", 3),
            Map.entry("ipv4", 4),
            Map.entry("st", 5),
            Map.entry("tcp", 6),
            Map.entry("cbt", 7),
            Map.entry("egp", 8),
            Map.entry("igp", 9),
            Map.entry("bbn-rcc-mon", 10),
            Map.entry("nvp-ii", 11),
            Map.entry("pup", 12),
            Map.entry("argus", 13),
            Map.entry("emcon", 14),
            Map.entry("xnet", 15),
            Map.entry("chaos", 16),
            Map.entry("udp", 17),
            Map.entry("mux", 18),
            Map.entry("dcn-meas", 19),
            Map.entry("hmp", 20),
            Map.entry("prm", 21),
            Map.entry("xns-idp", 22),
            Map.entry("trunk-1", 23),
            Map.entry("trunk-2", 24),
            Map.entry("leaf-1", 25),
            Map.entry("leaf-2", 26),
            Map.entry("rdp", 27),
            Map.entry("irtp", 28),
            Map.entry("iso-tp4", 29),
            Map.entry("netblt", 30),
            Map.entry("mfe-nsp", 31),
            Map.entry("merit-inp", 32),
            Map.entry("dccp", 33),
            Map.entry("3pc", 34),
            Map.entry("idpr", 35),
            Map.entry("xtp", 36),
            Map.entry("ddp", 37),
            Map.entry("idpr-cmtp", 38),
            Map.entry("tp++", 39),
            Map.entry("il", 40),
            Map.entry("ipv6", 41),
            Map.entry("sdrp", 42),
            Map.entry("ipv6-route", 43),
            Map.entry("ipv6-frag", 44),
            Map.entry("idrp", 45),
            Map.entry("rsvp", 46),
            Map.entry("gre", 47),
            Map.entry("dsr", 48),
            Map.entry("bna", 49),
            Map.entry("esp", 50),
            Map.entry("ah", 51),
            Map.entry("i-nlsp", 52),
            Map.entry("swipe", 53),
            Map.entry("narp", 54),
            Map.entry("mobile", 55),
            Map.entry("tlsp", 56),
            Map.entry("skip", 57),
            Map.entry("ipv6-icmp", 58),
            Map.entry("ipv6-nonxt", 59),
            Map.entry("ipv6-opts", 60),
            Map.entry("cftp", 62),
            Map.entry("sat-expak", 64),
            Map.entry("kryptolan", 65),
            Map.entry("rvd", 66),
            Map.entry("ippc", 67),
            Map.entry("sat-mon", 69),
            Map.entry("visa", 70),
            Map.entry("ipcv", 71),
            Map.entry("cpnx", 72),
            Map.entry("cphb", 73),
            Map.entry("wsn", 74),
            Map.entry("pvp", 75),
            Map.entry("br-sat-mon", 76),
            Map.entry("sun-nd", 77),
            Map.entry("wb-mon", 78),
            Map.entry("wb-expak", 79),
            Map.entry("iso-ip", 80),
            Map.entry("vmtp", 81),
            Map.entry("secure-vmtp", 82),
            Map.entry("vines", 83),
            Map.entry("ttp", 84),
            Map.entry("nsfnet-igp", 85),
            Map.entry("dgp", 86),
            Map.entry("tcf", 87),
            Map.entry("eigrp", 88),
            Map.entry("ospfigp", 89),
            Map.entry("sprite-rpc", 90),
            Map.entry("larp", 91),
            Map.entry("mtp", 92),
            Map.entry("ax.25", 93),
            Map.entry("ipip", 94),
            Map.entry("micp", 95),
            Map.entry("scc-sp", 96),
            Map.entry("etherip", 97),
            Map.entry("encap", 98),
            Map.entry("gmtp", 100),
            Map.entry("ifmp", 101),
            Map.entry("pnni", 102),
            Map.entry("pim", 103),
            Map.entry("aris", 104),
            Map.entry("scps", 105),
            Map.entry("qnx", 106),
            Map.entry("a/n", 107),
            Map.entry("ipcomp", 108),
            Map.entry("snp", 109),
            Map.entry("compaq-peer", 110),
            Map.entry("ipx-in-ip", 111),
            Map.entry("vrrp", 112),
            Map.entry("pgm", 113),
            Map.entry("l2tp", 115),
            Map.entry("ddx", 116),
            Map.entry("iatp", 117),
            Map.entry("stp", 118),
            Map.entry("srp", 119),
            Map.entry("uti", 120),
            Map.entry("smp", 121),
            Map.entry("sm", 122),
            Map.entry("ptp", 123),
            Map.entry("isis over ipv4", 124),
            Map.entry("fire", 125),
            Map.entry("crtp", 126),
            Map.entry("crudp", 127),
            Map.entry("sscopmce", 128),
            Map.entry("iplt", 129),
            Map.entry("sps", 130),
            Map.entry("pipe", 131),
            Map.entry("sctp", 132),
            Map.entry("fc", 133),
            Map.entry("rsvp-e2e-ignore", 134),
            Map.entry("mobility header", 135),
            Map.entry("udplite", 136),
            Map.entry("mpls-in-ip", 137),
            Map.entry("manet", 138),
            Map.entry("hip", 139),
            Map.entry("shim6", 140),
            Map.entry("wesp", 141),
            Map.entry("rohc", 142)
    );
    public static final Map<Integer, String> ID_TO_PROTOCOL_NAME_MAP = Map.ofEntries(
            Map.entry(0, "hopopt"),
            Map.entry(1, "icmp"),
            Map.entry(2, "igmp"),
            Map.entry(3, "ggp"),
            Map.entry(4, "ipv4"),
            Map.entry(5, "st"),
            Map.entry(6, "tcp"),
            Map.entry(7, "cbt"),
            Map.entry(8, "egp"),
            Map.entry(9, "igp"),
            Map.entry(10, "bbn-rcc-mon"),
            Map.entry(11, "nvp-ii"),
            Map.entry(12, "pup"),
            Map.entry(13, "argus"),
            Map.entry(14, "emcon"),
            Map.entry(15, "xnet"),
            Map.entry(16, "chaos"),
            Map.entry(17, "udp"),
            Map.entry(18, "mux"),
            Map.entry(19, "dcn-meas"),
            Map.entry(20, "hmp"),
            Map.entry(21, "prm"),
            Map.entry(22, "xns-idp"),
            Map.entry(23, "trunk-1"),
            Map.entry(24, "trunk-2"),
            Map.entry(25, "leaf-1"),
            Map.entry(26, "leaf-2"),
            Map.entry(27, "rdp"),
            Map.entry(28, "irtp"),
            Map.entry(29, "iso-tp4"),
            Map.entry(30, "netblt"),
            Map.entry(31, "mfe-nsp"),
            Map.entry(32, "merit-inp"),
            Map.entry(33, "dccp"),
            Map.entry(34, "3pc"),
            Map.entry(35, "idpr"),
            Map.entry(36, "xtp"),
            Map.entry(37, "ddp"),
            Map.entry(38, "idpr-cmtp"),
            Map.entry(39, "tp++"),
            Map.entry(40, "il"),
            Map.entry(41, "ipv6"),
            Map.entry(42, "sdrp"),
            Map.entry(43, "ipv6-route"),
            Map.entry(44, "ipv6-frag"),
            Map.entry(45, "idrp"),
            Map.entry(46, "rsvp"),
            Map.entry(47, "gre"),
            Map.entry(48, "dsr"),
            Map.entry(49, "bna"),
            Map.entry(50, "esp"),
            Map.entry(51, "ah"),
            Map.entry(52, "i-nlsp"),
            Map.entry(53, "swipe"),
            Map.entry(54, "narp"),
            Map.entry(55, "mobile"),
            Map.entry(56, "tlsp"),
            Map.entry(57, "skip"),
            Map.entry(58, "ipv6-icmp"),
            Map.entry(59, "ipv6-nonxt"),
            Map.entry(60, "ipv6-opts"),
            Map.entry(62, "cftp"),
            Map.entry(64, "sat-expak"),
            Map.entry(65, "kryptolan"),
            Map.entry(66, "rvd"),
            Map.entry(67, "ippc"),
            Map.entry(69, "sat-mon"),
            Map.entry(70, "visa"),
            Map.entry(71, "ipcv"),
            Map.entry(72, "cpnx"),
            Map.entry(73, "cphb"),
            Map.entry(74, "wsn"),
            Map.entry(75, "pvp"),
            Map.entry(76, "br-sat-mon"),
            Map.entry(77, "sun-nd"),
            Map.entry(78, "wb-mon"),
            Map.entry(79, "wb-expak"),
            Map.entry(80, "iso-ip"),
            Map.entry(81, "vmtp"),
            Map.entry(82, "secure-vmtp"),
            Map.entry(83, "vines"),
            Map.entry(84, "ttp"),
            Map.entry(85, "nsfnet-igp"),
            Map.entry(86, "dgp"),
            Map.entry(87, "tcf"),
            Map.entry(88, "eigrp"),
            Map.entry(89, "ospfigp"),
            Map.entry(90, "sprite-rpc"),
            Map.entry(91, "larp"),
            Map.entry(92, "mtp"),
            Map.entry(93, "ax.25"),
            Map.entry(94, "ipip"),
            Map.entry(95, "micp"),
            Map.entry(96, "scc-sp"),
            Map.entry(97, "etherip"),
            Map.entry(98, "encap"),
            Map.entry(100, "gmtp"),
            Map.entry(101, "ifmp"),
            Map.entry(102, "pnni"),
            Map.entry(103, "pim"),
            Map.entry(104, "aris"),
            Map.entry(105, "scps"),
            Map.entry(106, "qnx"),
            Map.entry(107, "a/n"),
            Map.entry(108, "ipcomp"),
            Map.entry(109, "snp"),
            Map.entry(110, "compaq-peer"),
            Map.entry(111, "ipx-in-ip"),
            Map.entry(112, "vrrp"),
            Map.entry(113, "pgm"),
            Map.entry(115, "l2tp"),
            Map.entry(116, "ddx"),
            Map.entry(117, "iatp"),
            Map.entry(118, "stp"),
            Map.entry(119, "srp"),
            Map.entry(120, "uti"),
            Map.entry(121, "smp"),
            Map.entry(122, "sm"),
            Map.entry(123, "ptp"),
            Map.entry(124, "isis over ipv4"),
            Map.entry(125, "fire"),
            Map.entry(126, "crtp"),
            Map.entry(127, "crudp"),
            Map.entry(128, "sscopmce"),
            Map.entry(129, "iplt"),
            Map.entry(130, "sps"),
            Map.entry(131, "pipe"),
            Map.entry(132, "sctp"),
            Map.entry(133, "fc"),
            Map.entry(134, "rsvp-e2e-ignore"),
            Map.entry(135, "mobility header"),
            Map.entry(136, "udplite"),
            Map.entry(137, "mpls-in-ip"),
            Map.entry(138, "manet"),
            Map.entry(139, "hip"),
            Map.entry(140, "shim6"),
            Map.entry(141, "wesp"),
            Map.entry(142, "rohc")
    );

    public static Map<LookupCsvKey, Tag> lookupCsvParser(String filename) throws IOException {
        Map<LookupCsvKey, Tag> lookupMap = new HashMap<>();
        BufferedReader file = new BufferedReader(new FileReader(filename));

        // Skip header
        String line = file.readLine();
        while ((line = file.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length < 3) {
                continue;
            }
            LookupCsvKey key = new LookupCsvKey(Integer.valueOf(parts[0].trim()), getProtocolId(parts[1].trim()));
            lookupMap.put(key, new Tag(parts[2].trim()));
        }
        file.close();
        return lookupMap;
    }

    public static Integer getProtocolId(String protocol){
        Integer id = PROTOCOL_NAME_TO_ID_MAP.get(protocol.toLowerCase());
        if (id != null){
            return id;
        }
        throw new InvalidProtocolException("Invalid protocol: " + protocol);
    }

    public static String getProtocolFromId(Integer id){
        String protocol = ID_TO_PROTOCOL_NAME_MAP.get(id);
        if (id != null){
            return protocol;
        }
        throw new InvalidProtocolException("Inavalid protocol id passed - " + id );
    }

    public static void exportTagCounts(String filename, Map<Tag, Integer> tagCounts) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, true))) {
            outFile.write(TAG_COUNT_HEADER);
            outFile.newLine();

            for (Map.Entry<Tag, Integer> entry : tagCounts.entrySet()) {
                outFile.write(entry.getKey().getTagName() + "," + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Unable to open file " + filename, e);
        }
    }

    public static void exportPortProtocolCount(String filename, Map<PortProtocol, Integer> map) throws IOException {
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename, true))) {
            outFile.write(PORT_PROTO_COUNT_HEADER);
            outFile.newLine();

            for (Map.Entry<PortProtocol, Integer> entry : map.entrySet()) {
                outFile.write(entry.getKey().getPort() + "," + entry.getKey().getProtocol() + ", " + entry.getValue());
                outFile.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Unable to open file " + filename, e);
        }
    }
}
