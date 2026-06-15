import {create} from 'zustand';
import {downloadBinaryContent, getBinaryContent} from '../api/binaryContent';
import {BinaryContentDto, BinaryContentStatus} from "@/types/api.ts";

export interface BinaryContentInfo {
  url?: string;
  contentType: string;
  fileName: string;
  size: number;
  status: BinaryContentStatus;
  revokeUrl?: () => void;
}

interface BinaryContentStore {
  binaryContents: Record<string, BinaryContentInfo>;
  fetchBinaryContent: (id: string) => Promise<BinaryContentInfo | null>;
  updateBinaryContentStatus: (updated: BinaryContentDto) => Promise<void>;
  clearBinaryContent: (id: string) => void;
  clearBinaryContents: (ids: string[]) => void;
  clearAllBinaryContents: () => void;
}

const useBinaryContentStore = create<BinaryContentStore>((set, get) => ({
  binaryContents: {},
  fetchBinaryContent: async (id) => {
    // 이미 가져온 정보가 있다면 재사용
    if (get().binaryContents[id]) {
      return get().binaryContents[id];
    }

    try {
      const binaryContent = await getBinaryContent(id);
      const { contentType, fileName, size, status  } = binaryContent;

      const binaryContentInfo: BinaryContentInfo = {
        contentType,
        fileName,
        size,
        status,
      };

      if (status === BinaryContentStatus.SUCCESS) {
        const downloadResult = await downloadBinaryContent(id);
        const imageObjectURL = URL.createObjectURL(downloadResult.blob);

        binaryContentInfo.url = imageObjectURL;
        binaryContentInfo.revokeUrl = () => URL.revokeObjectURL(imageObjectURL);
      }

      set((state) => ({
        binaryContents: {
          ...state.binaryContents,
          [id]: binaryContentInfo
        }
      }));

      return binaryContentInfo;
    } catch (error) {
      console.error('첨부파일 정보 조회 실패:', error);
      return null;
    }
  },
  clearBinaryContent: (id) => {
    const { binaryContents } = get();
    const content = binaryContents[id];
    if (content?.revokeUrl) {
      content.revokeUrl();
      set((state) => {
        const { [id]: removed, ...rest } = state.binaryContents;
        return { binaryContents: rest };
      });
    }
  },
  clearBinaryContents: (ids) => {
    const { binaryContents } = get();
    const idsToRevoke: string[] = [];
    
    // First pass: find existing IDs and revoke URLs
    ids.forEach(id => {
      const content = binaryContents[id];
      if (content) {
        if (content.revokeUrl) {
          content.revokeUrl();
        }
        idsToRevoke.push(id);
      }
    });
    
    // Only update state if some content was actually revoked
    if (idsToRevoke.length > 0) {
      set((state) => {
        const newBinaryContents = { ...state.binaryContents };
        idsToRevoke.forEach(id => {
          delete newBinaryContents[id];
        });
        return { binaryContents: newBinaryContents };
      });
    }
  },
  clearAllBinaryContents: () => {
    const { binaryContents } = get();
    Object.values(binaryContents).forEach(content => {
      if (content.revokeUrl) {
        content.revokeUrl();
      }
    });
    set({ binaryContents: {} });
  },
  updateBinaryContentStatus: async (updated: BinaryContentDto) => {
    if (updated.status === BinaryContentStatus.SUCCESS) {
      console.log(`${updated.id} 상태가 SUCCESS로 변경됨`);
      // 성공 상태가 되면 실제 파일 다운로드
      const downloadResult = await downloadBinaryContent(updated.id);
      const imageObjectURL = URL.createObjectURL(downloadResult.blob);

      set((state) => {

        return ({
          binaryContents: {
            ...state.binaryContents,
            [updated.id]: {
              ...updated,
              url: imageObjectURL,
              status: BinaryContentStatus.SUCCESS,
              revokeUrl: () => URL.revokeObjectURL(imageObjectURL)
            }
          }
        })
      } );

    } else if (status === BinaryContentStatus.FAIL) {
      console.log(`${updated.id} 상태가 FAIL로 변경됨`);
      set((state) => ({
        binaryContents: {
          ...state.binaryContents,
          [updated.id]: {
            ...updated,
            status: BinaryContentStatus.FAIL
          }
        }
      }));

    } else {
      console.log(`${updated.id} 상태가 여전히 PROCESSING임`);
    }
  }
}));

export default useBinaryContentStore; 