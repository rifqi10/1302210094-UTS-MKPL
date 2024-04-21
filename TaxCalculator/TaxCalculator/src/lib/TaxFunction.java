package lib;

public class TaxFunction {

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus
	 * dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan
	 * bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan)
	 * dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena
	 * pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah
	 * sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya
	 * ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */

	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible,
			boolean isMarried, int numberOfChildren) {

		validateNumberOfMonths(numberOfMonthWorking);

		numberOfChildren = limitNumberOfChildren(numberOfChildren);

		int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);

		int taxableIncome = calculateTaxableIncome(monthlySalary, otherMonthlyIncome, numberOfMonthWorking, deductible,
				nonTaxableIncome);

		return calculateTaxAmount(taxableIncome);
	}

	private static void validateNumberOfMonths(int numberOfMonthWorking) {
		if (numberOfMonthWorking > 12) {
			throw new IllegalArgumentException("Invalid number of months worked in a year");
		}
	}

	private static int limitNumberOfChildren(int numberOfChildren) {
		return Math.min(numberOfChildren, 3);
	}

	private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
		int nonTaxableIncome = 54000000;

		if (isMarried) {
			nonTaxableIncome += 4500000;
		}

		nonTaxableIncome += numberOfChildren * 4500000;

		return nonTaxableIncome;
	}

	private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking,
			int deductible, int nonTaxableIncome) {
		return ((monthlySalary + otherMonthlyIncome) * numberOfMonthWorking) - deductible - nonTaxableIncome;
	}

	private static int calculateTaxAmount(int taxableIncome) {
		int tax = (int) Math.round(0.05 * taxableIncome);
		return Math.max(0, tax);
	}

}
