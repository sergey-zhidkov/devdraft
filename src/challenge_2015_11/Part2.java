package challenge_2015_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class CostCalculator
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
        int numTestCases = Integer.parseInt(in.readLine().trim());

        for (int i=0; i<numTestCases; i++)
        {
            int basePrice = Integer.parseInt(in.readLine().trim());
            String addressString = in.readLine();
            Address addr = new Address(addressString);

            int taxAmount = TaxCalculator.calculateTax(basePrice, addr.getState());
            int shippingAmount = ShippingCalculator.calculateShipping(addr.getZipCode());

            System.out.println (basePrice + taxAmount + shippingAmount);
        }
    }
}

class Address
{
    private final String addressLine;
    private final String streetAddress;
    private final String cityName;
    private final String stateName;

    public Address (final String addressLine)
    {
        this.addressLine = addressLine;
        String [] addressParts = addressLine.split(",");
        int len = addressParts.length;
        streetAddress = addressParts[0].trim();
        cityName = addressParts[len - 2].trim();
        stateName = addressParts[len - 1].trim().split(" ")[0];
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public String getCityName()
    {
        return cityName;
    }

    public String getState()
    {
        return stateName;
    }

    public int getZipCode()
    {
        String [] addressParts = addressLine.split(",");
        String partWithZipCode = addressParts[addressParts.length - 1];
        int consecutiveDigits = 0;

        //search for something that matches 5 consecutive digits
        for (int i = 0; i < partWithZipCode.length(); i++)
        {
            char c = partWithZipCode.charAt(i);
            if (Character.isDigit(c))
            {
                consecutiveDigits++;
                if (consecutiveDigits == 5)
                {
                    return Integer.parseInt(partWithZipCode.substring(i-4, i+1));
                }
            }
            else
            {
                consecutiveDigits = 0;
            }
        }

        //should never happen
        return 0;
    }
}

class TaxCalculator
{
    private static final int TAX_ARIZONA = 5;
    private static final int TAX_WASHINGTON = 9;
    private static final int TAX_CALIFORNIA = 6;
    private static final int TAX_DELAWARE = 0;
    private static final int TAX_OTHER = 7;

    public static int calculateTax(int orderAmount, String state)
    {
        state = state.toLowerCase();
        switch (state)
        {
            case "arizona":
            case "az": return (orderAmount / 100) * TAX_ARIZONA;
            case "washington":
            case "wa": return (orderAmount / 100) * TAX_WASHINGTON;
            case "california":
            case "ca": return (orderAmount / 100) * TAX_CALIFORNIA;
            case "delaware":
            case "de": return TAX_DELAWARE;
        }
        return (orderAmount / 100) * TAX_OTHER;
    }
}

class ShippingCalculator
{
    public static int calculateShipping(int zipCode)
    {
        if (zipCode > 75000)
        {
            return 10;
        }
        else if (zipCode >= 25000)
        {
            return 20;
        }
        else
        {
            return 30;
        }
    }
}
