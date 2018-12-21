package com.codecool.quest_store.model;

public class QSUser extends Person {
    public QSUser(int ID, String firstName, String lastName, String email, String className, String userType, String status) {
        super(ID, firstName, lastName, email, className, userType, status);
    }



    @Override
    public boolean equals(Object object) {

//        if (object == this) {
//            return true;
//        }
        if (!(object instanceof QSUser)) {
            return false;
        }
        QSUser user = (QSUser) object;

        if (Integer.compare(user.getId(),this.getId()) != 0) {return false;}


        if ( user.getFirstName().equals(this.getFirstName())
                &&  user.getLastName().equals(this.getLastName())
            && user.getEmail().equals(this.getEmail())
                &&  user.getClassName().equals(this.getClassName())
        && ( user.getUserType().equals(this.getUserType()) &&  user.getStatus().equals(this.getStatus()) )) {return true;}

      return false;
    }

    @Override
    public int hashCode() {
        return 17*this.getId() + this.getStatus().hashCode() + this.getClassName().hashCode();
    }
}
