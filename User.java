package bank;

class NoUserFindException extends Exception {
	NoUserFindException() {
	//his.printStackTrace();
	}
};

class User implements java.io.Serializable {
	private int systemNumber;
	private String firstname;
	private String lastname;
	private long pesel;
	private String adress;
	Account account;

        
	User(int sNo, String fname, String lname, long p, String adr, double money){
          /*  while((int)(Math.log10(pesel)+1) != 11) {
			System.out.println("Wrong pesel");
					pesel = in.nextLong();
					in.nextLine();
		}*/
            systemNumber = sNo;
            firstname = fname;
            lastname = lname;
            pesel = p;
            adress = adr;
            account = new Account (money);

	}

	void display () {
		System.out.println(systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources());
	}

        
        @Override
        public String toString() {
            return systemNumber + "\t" + firstname + "\t" + lastname + "\t" + pesel + "\t" + adress + "\t" + account.getResources();
        }
	int getNumber() {
		return systemNumber;
	}

	String getName () {
		return firstname;
	}

	String getLastName () {
		return lastname;
	}

	long getPesel () {
		return pesel;
	}

	String getAdress () {
		return adress;
	}

}
