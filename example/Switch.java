public class Switch {
	public static void main(String[] args) {
		int ranking = 2;
		String groomhouse = switch(ranking) {
			case 1 -> "동쪽집";
			case 2 -> "서쪽집";
			case 3 -> "남쪽집";
			default -> "지구집";
    };
    System.out.println(groomhouse);
  }
}
