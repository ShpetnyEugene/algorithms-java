package RPN;


import java.util.Stack;

public class RPN {


    static public double calc(String input) {
        String output = getExpression(input);
        double result = consider(output);
        return result;
    }

    static public String getExpression(String input) {

        StringBuilder output = new StringBuilder();

        Stack<Character> StackEx = new Stack<>();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            if ('=' == c) {
                // TODO end calculation
                //break;
            }

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isDigit(c)) {

                output.append(c);
                output.append(' ');
            } else {

                if (operator(c)) {

                    if (c == '(') {
                        StackEx.push(c);
                    } else if (c == ')') {
                        char s = StackEx.pop();

                        while (s != '(') {
                            output.append(s);
                            s = StackEx.pop();
                            output.append(' ');
                        }
                    } else {
                        if (StackEx.size() > 0) {

                            if (priority(c) <= priority(StackEx.peek())) {
                                output.append(StackEx.pop());
                                output.append(' ');
                            }
                        }
                        StackEx.push(c);
                    }
                }
            }
        }
        while (StackEx.size() > 0) {
            output.append(StackEx.pop());
            output.append(' ');
        }
        return output.toString();
    }


    static private double consider(String input) {

        double result = 0;

        Stack<Double> ExemTemp = new Stack<>();


        for (int i = 0; i < input.length(); i++) {

            if (Character.isDigit(input.charAt(i))) {
                String temp = "";
                while (Character.isDigit(input.charAt(i)) && input.charAt(i) != ' ') {
                    temp += input.charAt(i);;
                    i++;
                    if (i == input.length()) {
                        break;
                    }
                }
                ExemTemp.push(Double.parseDouble(temp));
                i--;
            } else if (operator(input.charAt(i))) {
                double firstValue = ExemTemp.pop();
                double secondValue = ExemTemp.pop();

                switch (input.charAt(i)) {
                    case '+':
                        result = firstValue + secondValue;
                        break;
                    case '-':
                        result = secondValue - firstValue;
                        break;
                    case '*':
                        result = secondValue * firstValue;
                        break;
                    case '/':
                        result = secondValue / firstValue;
                        break;
                    case '^':
                        result = Math.pow(secondValue,firstValue );
                        break;
                }
                ExemTemp.push(result);
            }
        }
        return result;
    }

    static private boolean operator(char a) {
        if (("()+-*/^".indexOf(a) == -1)) {
            return false;
        }
        return true;
    }


    static private int priority(char a) {
        switch (a) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            case '^':
                return 5;
            default:
                return 6;

        }
    }

}
