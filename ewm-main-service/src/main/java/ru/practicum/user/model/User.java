package ru.practicum.user.model;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность пользователь.
 *
 * @author Nikolay Radzivon
 * @Date 23.06.2024
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    /**
     * Идентификационный номер пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Адрес электронной почты.
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    /**
     * Имя пользователя.
     */
    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
