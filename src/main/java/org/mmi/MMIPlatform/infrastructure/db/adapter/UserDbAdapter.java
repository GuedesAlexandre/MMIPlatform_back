package org.mmi.MMIPlatform.infrastructure.db.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PermissionsEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.UserDaoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDbAdapter {

    private final UserDaoRepository userDaoRepository;
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    private final ModuleDaoRepository moduleDaoRepository;
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public List<UserDao> getAllUserDao() {
        return this.userDaoRepository.findAll();
    }

    public UserDao saveUserDao(UserDao userDao) {
        if (!userDao.getPassword().matches(PASSWORD_PATTERN)) {
            log.info("Password must be at least 8 characters long and include a digit, a lowercase letter, an uppercase letter, and a special character.");
        }
        try {
            userDao.setPassword(argon2PasswordEncoder.encode(userDao.getPassword()));

            userDao.setModuleDaos(
                    userDao.getModuleDaos().stream()
                            .map(moduleDao -> this.moduleDaoRepository.findAll().stream()
                                    .filter(moduleDao1 -> moduleDao1.getName().equals(moduleDao.getName())
                                            && moduleDao1.getUeName().equals(moduleDao.getUeName())
                                            && moduleDao1.getSemester().equals(moduleDao.getSemester()))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Module not found: " + moduleDao.getName())))
                            .collect(Collectors.toList())
            );
            this.userDaoRepository.save(userDao);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return userDao;
    }

    public UserDao getUserDaoById(UUID id) {
        return this.userDaoRepository.findById(id).orElse(null);
    }

    public UserDao getUserDaoByEmail(String email) {
        UserDao userDao = null;
        try {
            userDao = this.userDaoRepository.findByEmail(email);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        return userDao;
    }

    public String deleteUserDaoByEmail(String email) {
        UserDao userDao = this.userDaoRepository.findByEmail(email);
        try {
            this.userDaoRepository.delete(userDao);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "User deleted successfully";
    }

    public String updateUserDao(UserDao userDao) {
        try {
            this.userDaoRepository.save(userDao);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return "User updated successfully";
    }

    //JUST FOR TEST !!!!!
    @Scheduled(fixedRate = 60000000, initialDelay = 40000)
    @Transactional
    public void initAdminUser() {
        Set<String> checkDuplicateModule = new HashSet<>();
        List<ModuleDao> moduleDaoList = moduleDaoRepository.findAll().stream()
                .filter(m -> m.getName().equals("R.407 Dév Back") ||
                        m.getName().equals("R.313 Développement Back") ||
                        m.getName().equals("SAE.501 Développer pour le web ou Concevoir un dispositif interactif"))
                .filter(m -> checkDuplicateModule.add(m.getName()))
                .toList();
        UserDao userAdmin = UserDao.builder()
                .email("cherifa.boucetta@univ-eiffel.fr")
                .password("MMIPl@tform24!")
                .username("Cherifa Respo-MMI3")
                .firstName("cherifa")
                .name("boucetta")
                .phone("0607080910")
                .address("4 rue Gambetta")
                .city("Meaux")
                .country("France")
                .establishment("IUT-Meaux")
                .access(PermissionsEnum.valueOf("ADMIN"))
                .moduleDaos(moduleDaoList)
                .build();

        this.saveUserDao(userAdmin);
        log.info("Admin user are saved in database");
    }
}
