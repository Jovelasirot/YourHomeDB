package jovelAsirot.YourHomeDB.payloads;

import java.util.Set;

public record UserResponseFavoriteDTO(Set<Long> favoritePropertyIds) {
}
