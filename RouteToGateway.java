import java.util.ArrayList;
import java.util.Scanner;

public class RouteToGateway {
	int n;
	static int[][] matrix;
	static int[] gateWays;
	int[] hops;
	ArrayList<Integer> pTrack;

	RouteToGateway(int n, int[][] matrix, int[] gateWays) {
		this.n = n;
		this.matrix = matrix;
		this.gateWays = gateWays;
		int[] sDist;
		boolean caser = false;
		for (int i = 0; i < n; i++) {
			caser = false;
			for (int j = 0; j < gateWays.length; j++) {
				if (gateWays[j] == i) {
					caser = true;
					break;
				}
			}
			if (caser == false) {
				hops = new int[this.n - 1];
				sDist = computeDij(i);
				pForwardTable(sDist, i + 1);
			}
		}
	}

	public int smlFinder(int[] dist, boolean[] spt) {
		int min = Integer.MAX_VALUE, min_ind = -1;
		for (int i = 0; i < dist.length; i++) {
			if (!spt[i] && dist[i] != -1 && dist[i] <= min) {
				min = dist[i];
				min_ind = i;
			}
		}
		return min_ind;
	}

	public void setH(int value, int pos) {
		this.hops[pos] = value;
	}

	public int getH(int pos) {
		return this.hops[pos];
	}

	public int[] computeDij(int vertex) {
		int[] dist = new int[n];
		pTrack = new ArrayList<>();
		boolean[] visited = new boolean[n];
		int min_ind = -1;

		for (int i = 0; i < n; i++) {
			pTrack.add(-1);
			dist[i] = -1;
			visited[i] = false;
			if (i < n - 1)
				setH(-1, i);
		}

		dist[vertex] = 0;

		for (int j = 0; j < n; j++) {
			min_ind = smlFinder(dist, visited);

			if (min_ind != -1) {
				visited[min_ind] = true;
				pTrack.set(j, min_ind + 1);

				for (int k = 0; k < n; k++) {

					if (!visited[k] && matrix[min_ind][k] != 0 && matrix[min_ind][k] != -1) {

						if (dist[k] == -1) {
							dist[k] = dist[min_ind] + matrix[min_ind][k];
						} else if ((dist[min_ind] + matrix[min_ind][k]) < dist[k]) {
							dist[k] = dist[min_ind] + matrix[min_ind][k];
						}
					}

				}
			}

		}

		return dist;
	}

	public static void main(String args[]) {
		Scanner keyboard = new Scanner(System.in);
		int sz = keyboard.nextInt();
		int[][] mt = new int[sz][sz];
		for (int row = 0; row < sz; row++) {
			for (int col = 0; col < sz; col++) {
				mt[row][col] = keyboard.nextInt();
			}
		}
		String lastJo = keyboard.next();
		lastJo += keyboard.nextLine();
		String newJo[] = lastJo.split(" ");
		int[] gtWay = new int[newJo.length];
		for (int i = 0; i < newJo.length; i++)
			gtWay[i] = Integer.parseInt(newJo[i]) - 1;

		RouteToGateway ls = new RouteToGateway(sz, mt, gtWay);
	}

	public void pForwardTable(int[] s_dist, int start) {
		System.out.printf("%8s", "Forwarding Table for " + (start));
		System.out.printf("%n%5.5s %10s %12s%n", "To", "Cost", "Next Hop");

		for (int i = 0; i < gateWays.length; i++) {
			if (pTrack.contains(i + 1))
				System.out.printf("%5d %10d %12d%n", (gateWays[i] + 1), s_dist[gateWays[i]], pTrack.get(i + 1));
			else
				System.out.printf("%5d %10d %12d%n", (gateWays[i] + 1), s_dist[gateWays[i]], -1);

		}
	}

}