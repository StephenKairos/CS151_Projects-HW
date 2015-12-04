import java.util.Random;

public class ShuffleExperiment {
	public static void main(String[] args) {
		int[][] arr = new int[4][4];
		int num = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = num;
				num++;
			}
		}
		print(arr);
		print(shuffle(arr));
	}

	public static int[][] shuffle(int[][] arr) {
		int x = 3;
		int y = 3;
		int[][] shuffled = arr;
		Random r = new Random();
		for (int k = 0; k < 100; k++) {
			System.out.println("----------------------------------");
			int newX = -1;
			int newY = -1;
			do {
				newX = x;
				newY = y;
				int direction = r.nextInt(4);
				if (direction == 0) {
					newY++;
				} else if (direction == 1) {
					newX++;
				} else if (direction == 2) {
					newY--;
				} else if (direction == 3) {
					newX--;
				}
			} while (newX < 0 || newX > 3 || newY < 0 || newY > 3);
			shuffled[x][y] = shuffled[newX][newY];
			shuffled[newX][newY] = 15;
			x = newX;
			y = newY;
			print(shuffled);
		}
		return shuffled;
	}

	public static void print(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (j != 0) {
					System.out.print(", ");
				}
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
}
