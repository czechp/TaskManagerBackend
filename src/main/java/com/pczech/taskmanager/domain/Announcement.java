package com.pczech.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity(name = "announcements")
public class Announcement {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Title cannot be null")
    @NotBlank(message = "Title cannot be blank")
    @Length(min = 5, max = 255)
    private String title;

    @NotNull(message = "Content cannot be null")
    @NotBlank(message = "Content cannot be blank")
    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AnnouncementComment> comments = new LinkedHashSet<>();

    @CreationTimestamp()
    private LocalDateTime creationDate;

    @UpdateTimestamp()
    private LocalDateTime updateDate;

    @PreRemove()
    public void preRemove() {
        appUser.getAnnouncements().remove(this);
        this.appUser = null;
    }

    public void addComment(AnnouncementComment comment) {
        comment.setAnnouncement(this);
        comments.add(comment);
    }

    public void addAppUser(AppUser appUser) {
        this.appUser = appUser;
        appUser.getAnnouncements().add(this);
    }

    @Override()
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(id);
        hashCodeBuilder.append(title);
        hashCodeBuilder.append(content);
        hashCodeBuilder.append(appUser.getId());
        return hashCodeBuilder.toHashCode();
    }


}
