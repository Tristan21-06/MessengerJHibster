import { type IReaction } from '@/shared/model/reaction.model';
import { type IConversation } from '@/shared/model/conversation.model';

export interface IMessage {
  id?: number;
  texte?: string;
  reactions?: IReaction[] | null;
  conversations?: IConversation[] | null;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public texte?: string,
    public reactions?: IReaction[] | null,
    public conversations?: IConversation[] | null,
  ) {}
}
