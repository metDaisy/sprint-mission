package com.sprint.mission.discodeit.repository.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLSubQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sprint.mission.discodeit.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ChannelQDSLRepositoryImpl implements ChannelQDSLRepository {
    private final JPAQueryFactory queryFactory;
    private final QChannel qChannel = QChannel.channel;
    private final QMessage qMessage = QMessage.message;
    private final QReadStatus qReadStatus = QReadStatus.readStatus;

    @Override
    public List<Channel> findVisibleToWithLastMsgAt(UUID userId) {
        return queryFactory.select(qChannel, getLastMsgAtSubQuery())
                .from(qChannel)
                .where(qChannel.type.eq(ChannelType.PUBLIC)
                        .or(qChannel.id.in(getJoinedPrivateChannels(userId))))
                .fetch()
                .stream()
                .map(this::injectLastMsgAt)
                .toList();
    }

    @Override
    public Optional<Channel> findByIdWithLastMsgAt(UUID id) {
        Channel channel = queryFactory.selectFrom(qChannel)
                .where(qChannel.id.eq(id))
                .fetchOne();
        Optional<Channel> result = Optional.ofNullable(channel);
        Instant lastMessageAt = queryFactory.select(qMessage.createdAt.max())
                .from(qMessage)
                .where(qMessage.channel.id.eq(id))
                .fetchOne();
        result.ifPresent(c -> c.setLastMessageAt(lastMessageAt));
        return result;
    }

    @Override
    public List<Channel> findAllWithLastMsgAt() {
        return queryFactory
                .select(qChannel, getLastMsgAtSubQuery())
                .from(qChannel)
                .fetch()
                .stream()
                .map(this::injectLastMsgAt)
                .toList();
    }

    private Channel injectLastMsgAt(Tuple tuple) {
        Channel channel = tuple.get(qChannel);
        Instant lastMessageAt = tuple.get(getLastMsgAtSubQuery());
        Objects.requireNonNull(channel).setLastMessageAt(lastMessageAt);
        return channel;
    }

    private JPQLSubQuery<Instant> getLastMsgAtSubQuery() {
        return JPAExpressions.select(qMessage.createdAt.max())
                .from(qMessage)
                .where(qMessage.channel.id.eq(qChannel.id));
    }

    private JPQLSubQuery<UUID> getJoinedPrivateChannels(UUID userId) {
        return JPAExpressions.select(qReadStatus.channel.id)
                .from(qReadStatus)
                .where(qReadStatus.user.id.eq(userId));
    }
}
