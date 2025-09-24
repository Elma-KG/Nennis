package is.hi.hbv501g.nennis.Persistence.Entities;


@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Tag {
}
