import useUserListStore from '@/stores/userListStore';
import {UserDto} from '@/types/api';
import {useEffect, useState} from 'react';
import MemberItem from './MemberItem';
import {MemberHeader, StyledMemberList, MemberHeaderContent} from './styles';
import useAuthStore from '@/stores/authStore';
import MemberDetailModal from './MemberDetailModal';
import NotificationIcon from '../Notification/NotificationIcon';
import { useSseStore } from '@/stores/sseStore.ts';

function MemberList(): JSX.Element {
  const users = useUserListStore((state) => state.users);
  const replaceUser = useUserListStore((state) => state.replaceUser);
  const removeUser = useUserListStore((state) => state.removeUser);
  const fetchUsers = useUserListStore((state) => state.fetchUsers);
  const { currentUser } = useAuthStore();
  const [selectedMember, setSelectedMember] = useState<UserDto | null>(null);
  const { subscribe, unsubscribe, isConnected } = useSseStore();

  useEffect(() => {
    fetchUsers();
  }, [fetchUsers]);

  // 멤버 정렬: 내 계정 > 온라인 상태 > 사용자명
  const sortedUsers = [...users].sort((a: UserDto, b: UserDto) => {
    // 내 계정을 최상단에 배치
    if (a.id === currentUser?.id) return -1;
    if (b.id === currentUser?.id) return 1;

    // 온라인 상태로 정렬
    if (a.online && !b.online) return -1;
    if (!a.online && b.online) return 1;

    // 사용자명으로 정렬
    return a.username.localeCompare(b.username);
  });

  useEffect(() => {
    if (isConnected) {
      subscribe('users.created', (user: UserDto) => {
        replaceUser(user);
      })
      subscribe('users.updated', (user: UserDto) => {
        replaceUser(user);
      })
      subscribe('users.deleted', (user: UserDto) => {
        removeUser(user.id);
      })
    }

    return () => {
      unsubscribe('users.created');
      unsubscribe('users.updated');
      unsubscribe('users.deleted');
    }
  }, [subscribe, isConnected, currentUser])

  return (
      <StyledMemberList>
        <MemberHeader>
          <MemberHeaderContent>
            멤버 목록 - {users.length}
            <NotificationIcon />
          </MemberHeaderContent>
        </MemberHeader>
        {sortedUsers.map(user => (
            <div key={user.id} onClick={() => setSelectedMember(user)}>
              <MemberItem key={user.id} member={user} />
            </div>
            
        ))}
        {
          selectedMember && (
          <MemberDetailModal 
            member={selectedMember}
            onClose={() => setSelectedMember(null)}
          />
        )}
      </StyledMemberList>
  );
}

export default MemberList; 