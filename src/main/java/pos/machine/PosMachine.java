package pos.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static pos.machine.ItemDataLoader.loadAllItemInfos;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        String printReceipt = generateReceipt(barcodes);
        return printReceipt;
    }

    public List<String> getItemName(List<String> barcodes){
        List<String> items = new ArrayList<String>();

        for(int x = 1; x <= loadAllItemInfos().size(); x++){
            for(int y = 1; y <= barcodes.size(); y++){
                if(loadAllItemInfos().get(x-1).getBarcode().equals(barcodes.get(y-1))){
                    items.add(loadAllItemInfos().get(x-1).getName());
                }
            }
        }
        return items;
    }

    public List<Integer> calculateItemCount(List<String> barcodes){
        List<Integer> itemCount = new ArrayList<Integer>();
        List<String> listWithoutDuplicates = barcodes.stream().distinct().collect(Collectors.toList());
//        for(int x=1; x <=listWithoutDuplicates.size(); x++){
//            itemCount.add(loadAllItemInfos().get(x-1).getPrice());
//        }
        int countFirstBCode = Collections.frequency(barcodes,loadAllItemInfos().get(0).getBarcode());
        int countSecondBCode = Collections.frequency(barcodes,loadAllItemInfos().get(1).getBarcode());
        int countThirdBCode = Collections.frequency(barcodes,loadAllItemInfos().get(2).getBarcode());
        itemCount.add(countFirstBCode);
        itemCount.add(countSecondBCode);
        itemCount.add(countThirdBCode);
        System.out.println(itemCount);
        return itemCount;
    }

    public List<Integer> computeItems(List<Integer> itemCount){
        List<Integer> subtotal = new ArrayList<Integer>();
        for(int x = 1; x <= itemCount.size(); x++){
            subtotal.add(loadAllItemInfos().get(x-1).getPrice() * itemCount.get(x-1));
        }
        return subtotal;
    }
    public int computeTotal(List<Integer> itemTotal){
        int total =0;
        for(int x = 1; x <= itemTotal.size(); x++){
            total += itemTotal.get(x-1);
        }
        return total;
    }

    public String generateReceipt(List<String> barcodes){
        List<String> items = getItemName(barcodes);
        String printReceipt ="";
        printReceipt += "***<store earning no money>Receipt***\n";

        List<Integer> intQty = calculateItemCount(barcodes);
        List<String> listWithoutDuplicates = items.stream().distinct().collect(Collectors.toList());
        List<Integer> itemSubtotal = computeItems(intQty);
        int total = computeTotal(itemSubtotal);
      for(int x=1; x<=listWithoutDuplicates.size(); x++){
          printReceipt+="Name: " + listWithoutDuplicates.get(x-1) + ", Quantity: " + intQty.get(x-1) + ", Unit price: " + loadAllItemInfos().get(x-1).getPrice() + " (yuan), Subtotal: "  +itemSubtotal.get(x-1)+ " (yuan)";
          printReceipt+="\n";
      }
        printReceipt+="----------------------\n";
        printReceipt+= "Total: "+ total + " (yuan)\n**********************";
        return printReceipt;
    }
}

//"***<store earning no money>Receipt***\n" +
//        "Name: Coca-Cola, Quantity: 4, Unit price: 3 (yuan), Subtotal: 12 (yuan)\n" +
//        "Name: Sprite, Quantity: 2, Unit price: 3 (yuan), Subtotal: 6 (yuan)\n" +
//        "Name: Battery, Quantity: 3, Unit price: 2 (yuan), Subtotal: 6 (yuan)\n" +
//        "----------------------\n" +
//        "Total: 24 (yuan)\n" +
//        "**********************"