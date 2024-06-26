package org.mse.rootdnsserver.service;

import java.util.List;
import org.mse.rootdnsserver.model.DnsEntry;
import org.mse.rootdnsserver.repository.DnsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DnsService {

    private static final Logger logger = LoggerFactory.getLogger(DnsService.class);
    private final DnsRepository dnsRepository;


    public DnsService(DnsRepository dnsRepository) {
        this.dnsRepository = dnsRepository;
    }


    public Optional<String> resolveDomain(String domain) {
        logger.info("Resolving domain: {}", domain);
        Optional<DnsEntry> dnsEntryOptional = dnsRepository.findByDomain(domain);
        if (dnsEntryOptional.isPresent()) {
            String ipAddress = dnsEntryOptional.get().getIpAddress();
            logger.info("Resolved domain {} to IP address: {}", domain, ipAddress);
            return Optional.of(ipAddress);
        } else {
            logger.warn("Domain {} not found", domain);
            return Optional.empty();
        }
    }


    public List<DnsEntry> getAllDomains() {
        return dnsRepository.findAll();
    }

}
