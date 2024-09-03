package util;

import constants.ApplicationConstants;
import exception.InvalidProtocolException;
import model.CsvLookupKey;
import model.Tag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class FlowRecordAnalyzerUtil {


    private static final Map<Integer, String> ID_TO_PROTOCOL_NAME_MAP = Map.ofEntries(
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

    public static Map<CsvLookupKey, Tag> lookupCsvParser(String filename) throws IOException {
        Map<CsvLookupKey, Tag> lookupMap = new HashMap<>();
        BufferedReader file = new BufferedReader(new FileReader(filename));

        String line = file.readLine();
        while ((line = file.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length < 3) {
                continue;
            }
            CsvLookupKey key = new CsvLookupKey(Integer.valueOf(parts[0].trim()), parts[1].trim());
            lookupMap.put(key, new Tag(parts[2].trim()));
        }
        file.close();
        return lookupMap;
    }


    public static String getProtocolFromId(Integer id) {
        String protocol = ID_TO_PROTOCOL_NAME_MAP.get(id);
        if (id != null) {
            return protocol;
        }
        throw new InvalidProtocolException(ApplicationConstants.ERROR_INVALID_PROTOCOL_ID_PASSED + id);
    }

}
