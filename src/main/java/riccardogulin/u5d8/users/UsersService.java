package riccardogulin.u5d8.users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import riccardogulin.u5d8.exceptions.BadRequestException;
import riccardogulin.u5d8.exceptions.NotFoundException;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepo;

	public User create(User u) {
		usersRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});

		return usersRepo.save(u);
	}

	public List<User> find() {
		return usersRepo.findAll();
	}

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));
	}

	public User findByIdAndUpdate(UUID id, User u) throws NotFoundException {
		User found = this.findById(id);

		found.setId(id);
		found.setName(u.getName());
		found.setSurname(u.getSurname());
		found.setEmail(u.getEmail());

		return usersRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		usersRepo.delete(found);
	}

}
