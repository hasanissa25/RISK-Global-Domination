package Game;

/**
 * @author      Hasan Issa
 *
 * This class takes a string of 4 words, and coverts it into a command.
 *
 */
public class Command
{
 private String commandWord;
 private String secondWord;
 private String thirdWord;
 private String fourthWord;


 public Command(String firstWord, String secondWord, String thirdWord,String fourthWord)
 {
  commandWord = firstWord;
  this.secondWord = secondWord;
  this.thirdWord = thirdWord;
  this.fourthWord = fourthWord;

 }


 public String getCommandWord() {
/**
 * @author      Hasan Issa
 *
 * @param commandWord This is the word that dictates what type of action will be taken due to this command.
 *                    commandWords include Attack,Map,Quit, and Pass
 *
 */
  return commandWord;
 }
 public void setCommandWord(String commandWord) {
  this.commandWord = commandWord;
 }
 public boolean isUnknown() {
  return (commandWord == null);
 }
 public String getSecondWord() {return secondWord; }
 public String getThirdWord() {
  return thirdWord;
 }
 public int getFourthWord() {
  return Integer.valueOf(fourthWord);
 }
 public boolean hasSecondWord() {
  return (secondWord != null);
 }
 public boolean hasThirdWord() {
  return (thirdWord != null);
 }
 public boolean hasFourthWord() { return (fourthWord != null); }
 @Override
 public String toString() {
  final StringBuilder sb = new StringBuilder("Command{");
  sb.append("commandWord='").append(commandWord).append('\'');
  sb.append(", secondWord='").append(secondWord).append('\'');
  sb.append(", thirdWord='").append(thirdWord).append('\'');
  sb.append(", fourthWord='").append(fourthWord).append('\'');
  sb.append('}');
  return sb.toString();
 }}

