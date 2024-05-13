package models;

public class Person {
    private int iduser,phone_number,age,is_verified;
    private String first_name, last_name,email,password,profile_picture,username,adress,google_id,hosted_domain,role,secrete;

    public int getIduser() {
        return iduser;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getIs_verified() {
        return is_verified;
    }

//    public void setIs_verified(int is_verified) {
//        this.is_verified = is_verified;
//    }
//
//    public String getProfile_picture() {
//        return profile_picture;
//    }
//
//    public void setProfile_picture(String profile_picture) {
//        this.profile_picture = profile_picture;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSecrete() {
        return secrete;
    }

    public void setSecrete(String secrete) {
        this.adress = secrete;
    }

//    public String getGoogle_id() {
//        return google_id;
//    }
//
//    public void setGoogle_id(String google_id) {
//        this.google_id = google_id;
//    }
//
//    public String getHosted_domain() {
//        return hosted_domain;
//    }
//
//    public void setHosted_domain(String hosted_domain) {
//        this.hosted_domain = hosted_domain;
//    }
//


    public Person() {}

    public Person(String first_name, String last_name, String adress, int phone_number, String role, String email, String username, String password, int is_verified) {
     //   this.iduser = iduser;
        this.phone_number = phone_number;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.is_verified = is_verified;
     //   this.profile_picture = profile_picture;
        this.username = username;
        this.adress = adress;
//        this.google_id = google_id;
//        this.hosted_domain = hosted_domain;
//        this.date_of_birth = date_of_birth;
    }

//    public Person(String first_name, String last_name, String email, String password, int is_verified) {
////        this.iduser = iduser;
////        this.phone_number = phone_number;
////        this.age = age;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.email = email;
//        this.password = password;
//        this.is_verified = is_verified;
////        this.profile_picture = profile_picture;
////        this.username = username;
////        this.adress = adress;
////        this.google_id = google_id;
////        this.hosted_domain = hosted_domain;
////        this.date_of_birth = date_of_birth;
//    }

    public Person(String first_name, String last_name, int age) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
    }

//    public Person(int iduser, String first_name, String last_name, int age) {
//        this.iduser = iduser;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.age = age;
//    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "iduser=" + iduser +
                ", phone_number=" + phone_number +
                ", age=" + age +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", is_verified='" + is_verified + '\'' +
                ", profile_picture='" + profile_picture + '\'' +
                ", username='" + username + '\'' +
                ", adress='" + adress + '\'' +
                ", google_id='" + google_id + '\'' +
                ", hosted_domain='" + hosted_domain + '\'' +
                '}';
    }
}
