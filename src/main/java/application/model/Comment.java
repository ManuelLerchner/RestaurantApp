package application.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "FieldHandler"})
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "headline")
    private String headline;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private Integer rating;

    @JsonIgnoreProperties("comments")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnoreProperties("comments")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return id != null && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    //Main-Method to create comments for database
    public static void main(String[] args) {
        int repeat = 3 ;

            String[] headlineStringGoodStart = {"Amazing ", "Wholesome ", "Good ", "Fun ", "Relaxing ", "Remarkable ", "Outstanding ", "Terrific ", "Impeccable "};
            String[] headlinePArt2Good = {"Day ", "Time ", "Food ", "Experience ", "Service "};
            String[] GoodPart3 = {"in Munich", "in this restaurant", "in this awesome place", "","in this hidden treasure", "here", "for us"};

            String[] textStringGoodStart = {"We really liked the ", "We were surprised by the ", "A lucky find in Munich where we enjoyed the ", "Fun ", "Fantastic ", "Hyped by the "};
            String[] textPArt2Good = {"great food in this restaurant. ", "meal of the day in this awesome place. ", "recommendations of the cook for us. ", "welcoming ambience here. ", "professional service. ", "kind servers. "};
            String[] text3 = {"The background music was great too!", "So happy!", "Yummy!", "Loved it!", "Great for our kids too!"};

            int[] goodRating = {4, 5};

            String[] headlineStringBadStart = {"Worst ", "Unwelcoming ", "Unprofessional ", "Disappointing "};
            String[] headlinePArt2Bad = {"service ", "ambience ", "Food ", "Experience "};
            String[] badPart3 = {"", "in this restaurant", "in this hellhole", "here", "for us", "in this joke of a dinner place"};

            String[] textStringBadStart = {"We were disappointed by the ", "We were negatively surprised by the ", "A mistake to enjoy lunch here in Munich where we experienced a ", "Horrible "};
            String[] textPArt2Bad = {"mildly convincing food. ", "server who ignored us for hours. ", "bad hygiene in the entire place. ", "weird and rude service of everyone in this place. "};
            String[] textBad3 = {"Will never come back!", "Maybe this was an exception, but really bad!", "We had looked forward to going here and was totally disappointed.  We will never go back.", "For tourists an ugly experiencie", "Hope you don’t have such a bad time!"};

            int[] badRating = {1, 2, 3};

            String[] dateMonth = {"01", "02", "03", "04", "05", "06", "07"};
            String[] dateDay = {"01", "02", "03", "04", "05", "06", "07","08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};

            for (int i = 0; i < repeat; i++) {
            String date = "2022-" + dateMonth[(int) (Math.random() * 6)] + "-" + dateDay[(int) (Math.random() * 28)];
            String headline;
            String text;
            int rating;
            if (Math.random() < 0.1) {
                headline = headlineStringBadStart[(int) (Math.random() * 3)] + headlinePArt2Bad[(int) (Math.random() * 3)] + badPart3[(int) (Math.random() * 4)];
                rating = badRating[(int) (Math.random() * 2)];
                text = textStringBadStart[(int) (Math.random() * 3)] + textPArt2Bad[(int) (Math.random() * 3)] + textBad3[(int) (Math.random() * 3)];
            } else {
                headline = headlineStringGoodStart[(int) (Math.random() * 8)] + headlinePArt2Good[(int) (Math.random() * 4)] + GoodPart3[(int) (Math.random() * 6)];
                rating = goodRating[(int) (Math.random() * 2)];
                text = textStringGoodStart[(int) (Math.random() * 5)] + textPArt2Good[(int) (Math.random() * 5)] + text3[(int) (Math.random() * 4)];
            }

            if (i < repeat-1) {
                System.out.println("{ \"date\" : \"" + date + "\",\n" +
                        "\"headline\" : \"" + headline + "\", \"text\" : \"" + text + "\",\n" +
                        "\"rating\" : " + rating + "},");
            }
            else {
                System.out.println("{ \"date\" : \"" + date + "\",\n" +
                        "\"headline\" : \"" + headline + "\", \"text\" : \"" + text + "\",\n" +
                        "\"rating\" : " + rating + "}");
            }
        }
    }


}