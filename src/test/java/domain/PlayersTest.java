package domain;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlayersTest {
    @Test
    @DisplayName("특정 이름을 가진 플레이어에게 카드를 제공한다.")
    void giveCardToGamerTest() {
        // given
        List<String> usernames = List.of("a", "b", "c");
        Players players = new Players(usernames);
        String username = "a";
        Card card = Card.HEART_JACK;
        // when
        players.giveCard(username, card);
        // then
        List<Card> cards = players.getPlayerCard(username);
        Assertions.assertThat(cards).contains(card);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("특정 플레이어가 카드를 더 받을 수 있는지 확인합니다.")
    void canGetMoreCardTest(List<Card> cards, boolean expected) {
        //given
        List<String> usernames = List.of("김", "이", "박");
        Players players = new Players(usernames);
        cards.forEach(card -> players.giveCard("김", card));

        //when & then
        Assertions.assertThat(players.canGetMoreCard("김")).isEqualTo(expected);
    }

    public static Stream<Arguments> canGetMoreCardTest() {
        return Stream.of(
                Arguments.of(List.of(Card.HEART_TEN, Card.HEART_ACE), true),
                Arguments.of(List.of(Card.HEART_TEN, Card.HEART_ACE, Card.CLOVER_QUEEN), true),
                Arguments.of(List.of(Card.HEART_TEN, Card.HEART_QUEEN, Card.CLOVER_THREE), false),
                Arguments.of(List.of(Card.HEART_TEN, Card.DIA_JACK, Card.CLOVER_TWO), false)
        );
    }
}