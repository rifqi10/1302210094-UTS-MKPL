package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	// Informasi dasar pegawai
	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;

	// Informasi tanggal bergabung
	private int yearJoined;
	private int monthJoined;
	private int dayJoined;
	private int monthWorkingInYear;

	// Informasi status pegawai
	private boolean isForeigner;
	private Gender gender;

	// Informasi gaji
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;

	// Informasi pasangan
	private String spouseName;
	private String spouseIdNumber;

	// Informasi anak-anak
	private List<String> childNames;
	private List<String> childIdNumbers;

	// Enum untuk jenis kelamin
	public enum Gender {
		MALE, FEMALE
	}

	// Konstanta untuk grade gaji
	private static final int GRADE_1_SALARY = 3000000;
	private static final int GRADE_2_SALARY = 5000000;
	private static final int GRADE_3_SALARY = 7000000;

	// Konstruktor untuk membuat objek Employee
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
			int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Gender gender) {
		// Inisialisasi informasi pegawai
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.dayJoined = dayJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;

		// Inisialisasi list anak-anak
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}

	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya
	 * (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3:
	 * 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */

	// Method untuk mengatur gaji bulanan berdasarkan grade
	public void setMonthlySalary(int grade) {
		// Mengatur gaji bulanan berdasarkan grade dengan mempertimbangkan
		// kewarganegaraan
		switch (grade) {
			case 1:
				monthlySalary = isForeigner ? GRADE_1_SALARY + (int) (GRADE_1_SALARY * 0.5) : GRADE_1_SALARY;
				break;
			case 2:
				monthlySalary = isForeigner ? GRADE_2_SALARY + (int) (GRADE_2_SALARY * 0.5) : GRADE_2_SALARY;
				break;
			case 3:
				monthlySalary = isForeigner ? GRADE_3_SALARY + (int) (GRADE_3_SALARY * 0.5) : GRADE_3_SALARY;
				break;
			default:
				throw new IllegalArgumentException("Invalid grade");
		}
	}

	// Method untuk mengatur potongan gaji tahunan
	public void setAnnualDeductible(int deductible) {
		this.annualDeductible = deductible;
	}

	// Method untuk mengatur pendapatan tambahan
	public void setAdditionalIncome(int income) {
		this.otherMonthlyIncome = income;
	}

	// Method untuk mengatur data pasangan
	public void setSpouse(String spouseName, String spouseIdNumber) {
		// Menyimpan informasi pasangan
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}

	// Method untuk menambahkan data anak
	public void addChild(String childName, String childIdNumber) {
		// Menambahkan informasi anak ke list
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}

	// Method untuk menghitung pajak tahunan
	public int getAnnualIncomeTax() {
		// Menghitung berapa lama pegawai bekerja dalam setahun ini
		LocalDate date = LocalDate.now();
		if (date.getYear() == yearJoined) {
			monthWorkingInYear = date.getMonthValue() - monthJoined;
		} else {
			monthWorkingInYear = 12;
		}
		// Menghitung pajak menggunakan fungsi kalkulasi pajak
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible,
				spouseIdNumber.equals(""), childIdNumbers.size());
	}
}
