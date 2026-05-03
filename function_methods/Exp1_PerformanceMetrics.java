package org.cloudbus.cloudsim.examples;

import java.text.DecimalFormat;
import java.util.*;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;

public class Exp1_PerformanceMetrics {

    private static List<Cloudlet> cloudletList;
    private static List<Vm> vmlist;

    public static void main(String[] args) {

        Log.printLine("Starting CloudSim Example with Metrics...");

        try {
            // Step 1: Initialize CloudSim  
            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;

            CloudSim.init(num_user, calendar, trace_flag);

            // Step 2: Create Datacenter
            Datacenter datacenter0 = createDatacenter("Datacenter_0");

            // Step 3: Create Broker
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();

            // Step 4: Create VM
            vmlist = new ArrayList<Vm>();

            int vmid = 0;
            int mips = 1000;
            long size = 10000;
            int ram = 512;
            long bw = 1000;
            int pesNumber = 1;
            String vmm = "Xen";

            // TimeShared scheduling
            Vm vm = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm,
                    new CloudletSchedulerTimeShared());

            vmlist.add(vm);
            broker.submitVmList(vmlist);

            // Step 5: Create Cloudlets (10 homogeneous tasks)
            cloudletList = new ArrayList<Cloudlet>();

            int fileSize = 300;
            int outputSize = 300;
            UtilizationModel utilizationModel = new UtilizationModelFull();

            for (int i = 0; i < 10; i++) {

                // length = workload (same for all → homogeneous)
                long length = 400000;

                Cloudlet cloudlet = new Cloudlet(i, length, pesNumber,
                        fileSize, outputSize,
                        utilizationModel, utilizationModel, utilizationModel);

                cloudlet.setUserId(brokerId);
                cloudlet.setVmId(vmid);

                cloudletList.add(cloudlet);
            }

            broker.submitCloudletList(cloudletList);

            // Step 6: Start Simulation
            CloudSim.startSimulation();

            List<Cloudlet> newList = broker.getCloudletReceivedList();

            CloudSim.stopSimulation();

            // Step 7: Print Results
            printCloudletList(newList);

            // ================= PERFORMANCE METRICS =================

            double makespan;
            double totalLatency = 0.0;
            double minStartTime = Double.MAX_VALUE;
            double maxFinishTime = Double.MIN_VALUE;

            for (Cloudlet cloudlet : newList) {

                double start = cloudlet.getExecStartTime();
                double finish = cloudlet.getFinishTime();

                // Find earliest start time
                if (start < minStartTime) {
                    minStartTime = start;
                }

                // Find latest finish time
                if (finish > maxFinishTime) {
                    maxFinishTime = finish;
                }

                // Latency = finish - start
                totalLatency += (finish - start);
            }

            // Makespan = total time from first start to last finish
            makespan = maxFinishTime - minStartTime;

            // Average latency
            double avgLatency = totalLatency / newList.size();

            // Throughput = number of tasks / total time
            double throughput = newList.size() / makespan;

            // Print Metrics
            Log.printLine("\n========== PERFORMANCE METRICS ==========");
            Log.printLine("Makespan: " + makespan);
            Log.printLine("Average Latency: " + avgLatency);
            Log.printLine("Throughput: " + throughput);

            Log.printLine("CloudSim finished!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Error occurred");
        }
    }

    // Datacenter creation
    private static Datacenter createDatacenter(String name) {

        List<Host> hostList = new ArrayList<Host>();
        List<Pe> peList = new ArrayList<Pe>();

        int mips = 1000;

        peList.add(new Pe(0, new PeProvisionerSimple(mips)));

        int hostId = 0;
        int ram = 2048;
        long storage = 1000000;
        int bw = 10000;

        hostList.add(new Host(hostId,
                new RamProvisionerSimple(ram),
                new BwProvisionerSimple(bw),
                storage,
                peList,
                new VmSchedulerTimeShared(peList)));

        String arch = "x86";
        String os = "Linux";
        String vmm = "Xen";
        double time_zone = 10.0;
        double cost = 3.0;

        DatacenterCharacteristics characteristics =
                new DatacenterCharacteristics(arch, os, vmm, hostList,
                        time_zone, cost, 0.05, 0.001, 0.0);

        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics,
                    new VmAllocationPolicySimple(hostList),
                    new LinkedList<Storage>(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }

    // Broker creation
    private static DatacenterBroker createBroker() {
        DatacenterBroker broker = null;
        try {
            broker = new DatacenterBroker("Broker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return broker;
    }

    // Output printing
    private static void printCloudletList(List<Cloudlet> list) {
        String indent = "    ";
        Log.printLine("\n========== OUTPUT ==========");

        DecimalFormat dft = new DecimalFormat("###.##");

        for (Cloudlet cloudlet : list) {
            Log.printLine(indent + cloudlet.getCloudletId()
                    + indent + cloudlet.getStatus()
                    + indent + dft.format(cloudlet.getExecStartTime())
                    + indent + dft.format(cloudlet.getFinishTime()));
        }
    }
}


















































//🔹 Makespan
//
//Total time taken from first cloudlet start to last cloudlet finish.
//
//🔹 Latency
//
//Time taken by each cloudlet (finish - start), averaged.
//
//🔹 Throughput
//
//Number of cloudlets completed per unit time.