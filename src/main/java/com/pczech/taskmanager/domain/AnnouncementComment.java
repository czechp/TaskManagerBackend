package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

@Entity(name = "announcement_comments")
@Data()
@SuperBuilder()
@NoArgsConstructor()
public class AnnouncementComment extends CommentSuperClass {

    @JsonIgnore()
    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    @PreRemove()
    public void preRemove() {
        announcement.getComments().remove(this);
        announcement = null;
    }

    @Override()
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(super.getId());
        hashCodeBuilder.append(super.getContent());
        hashCodeBuilder.append(announcement.getId());
        return hashCodeBuilder.toHashCode();
    }
}
