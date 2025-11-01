package com.xeno.crm_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xeno.crm_backend.model.Customer;

@RestController
@RequestMapping("/api/customers")
public class CustomerFilterController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/filter")
    public ResponseEntity<?> filterCustomers(@RequestBody List<Map<String, Object>> rules) {
        try {
            System.out.println("CustomerFilter - Received rules: " + rules);
            
            if (rules == null || rules.isEmpty()) {
                System.out.println("CustomerFilter - No rules provided, returning total count");
                long totalCount = mongoTemplate.count(new Query(), Customer.class);
                return ResponseEntity.ok(Map.of("count", totalCount));
            }
            
            Query query = new Query();
            Criteria combined = null;

            for (int i = 0; i < rules.size(); i++) {
                Map<String, Object> rule = rules.get(i);
                String field = (String) rule.get("field");
                String op = (String) rule.get("operator");
                Object value = rule.get("value");
                String condition = (String) rule.get("condition");

                System.out.println("CustomerFilter - Processing rule " + i + ": field=" + field + ", op=" + op + ", value=" + value + ", condition=" + condition);

                // Convert value to appropriate type
                Object convertedValue = value;
                if (value instanceof String) {
                    try {
                        convertedValue = Double.parseDouble((String) value);
                        System.out.println("CustomerFilter - Converted value to number: " + convertedValue);
                    } catch (NumberFormatException e) {
                        // Keep as string
                    }
                } else if (value instanceof Integer) {
                    convertedValue = ((Integer) value).doubleValue();
                }

                Criteria criteria;
                switch (op) {
                    case ">":
                        criteria = Criteria.where(field).gt(convertedValue);
                        break;
                    case "<":
                        criteria = Criteria.where(field).lt(convertedValue);
                        break;
                    case "=":
                        criteria = Criteria.where(field).is(convertedValue);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator: " + op);
                }

                if (i == 0) {
                    combined = criteria;
                } else if ("AND".equalsIgnoreCase(condition)) {
                    combined = new Criteria().andOperator(combined, criteria);
                } else {
                    combined = new Criteria().orOperator(combined, criteria);
                }
            }

            if (combined != null) {
                query.addCriteria(combined);
            }

            long count = mongoTemplate.count(query, Customer.class);
            System.out.println("CustomerFilter - Query result count: " + count);
            
            return ResponseEntity.ok(Map.of("count", count));
        } catch (Exception e) {
            System.err.println("CustomerFilter - Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to filter customers", "message", e.getMessage()));
        }
    }
}
