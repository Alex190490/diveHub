package alexDavid.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByEmail(String email);
    void deleteAllByEmail(String email);
}
