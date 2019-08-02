package com.thoughtworks.cyclerental.service;

import com.thoughtworks.cyclerental.dto.Customer;
import com.thoughtworks.cyclerental.dto.Cycle;
import com.thoughtworks.cyclerental.dto.Invoice;

import java.util.*;
import java.util.stream.Collectors;

public class RentalService {
    List<Invoice> invoices = new ArrayList<>();
    List<Cycle> cycles = new ArrayList<>();
    HashMap<Cycle, Customer> rentals = new HashMap<>();
    List<Customer> customers = new ArrayList<Customer>();

    public RentalService( List<Cycle> cycles, List<Customer> customers) {
        this.cycles = cycles;
        this.customers = customers;
    }

    public RentalService(){

    }

    public boolean rentCycle(int cycleId, int customerId) {
        Cycle cycle = cycles.stream().filter(c -> !c.isRented && c.id == cycleId).findFirst().get();
        Customer customer = customers.stream().filter(c -> c.id == customerId).findFirst().get();
        rentals.put(cycle, customer);
        cycle.isRented = true;
        cycle.rentedOn = new Date();
        return true;
    }

    public boolean returnCycle(int cycleId) {
        Cycle cycle = cycles.stream().filter(c -> c.isRented && c.id == cycleId).findFirst().get();
        rentals.remove(cycle);
        cycle.isRented = false;
        invoices.add(getPrice(cycle));
        System.out.println(invoices.get(invoices.size() - 1));
        return true;
    }

    public String generateInvoice () {
        return printInvoices(invoices);
    }

    public String printCycles() {
        StringBuilder cycleStr = new StringBuilder();
        for(Cycle cycle: cycles) {
            cycleStr.append(cycle).append("\n");
        }
        return cycleStr.toString();
    }

    public static Invoice getPrice(Cycle cycle) {
        Date now = new Date();
        long diff = cycle.rentedOn.getTime() - now.getTime();
        long totalDays = diff / (24 * 60 * 60 * 1000);
        if (cycle.noOfDays == 0) {
            Invoice invoice = new Invoice();
            invoice.descriptions.add(String.format("%d Rent", totalDays));
            invoice.amounts.add(totalDays * cycle.pricePerDay);
            return invoice;
        } else {
            Invoice invoice = new Invoice();
            invoice.descriptions.add(String.format("Base Rent for %d", cycle.noOfDays));
            invoice.amounts.add(cycle.basePrice);
            if(totalDays > cycle.noOfDays) {
                long extraDays = totalDays - cycle.noOfDays;
                double extraRent = extraDays * cycle.pricePerDay;
                invoice.descriptions.add(String.format("Extra Rent for %d extra days", cycle.noOfDays));
                invoice.amounts.add(extraRent);
            }
            return invoice;
        }
    }

    public String printCyclesReport (String brand) {
        List<Cycle> availableCycles = cycles.stream().filter(  c -> !c.isRented).collect(Collectors.toList());
        List<Cycle> cyclesInBrand = availableCycles.stream().filter( c -> c.brand.toLowerCase().contains(brand.toLowerCase())).collect(Collectors.toList());
        return printCycles();
    }

    private static String printInvoices(List<Invoice> invoices) {
        StringBuilder invoiceStr = new StringBuilder();
        for(Invoice invoice : invoices) {
            invoiceStr.append(invoice).append("\n");
            invoiceStr.append("=========================================");
        }
        return invoiceStr.toString();
    }




}
