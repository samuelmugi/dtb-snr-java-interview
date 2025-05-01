CREATE TABLE IF NOT EXISTS transactions
(
    id              UUID PRIMARY KEY,
    from_account_id UUID,
    to_account_id   UUID,
    amount          DECIMAL(19, 4) NOT NULL,
    type            VARCHAR(20)    NOT NULL,
    status          VARCHAR(20)    NOT NULL,
    created_at      TIMESTAMP      NOT NULL
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_transactions_from_account_id ON transactions (from_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_to_account_id ON transactions (to_account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_created_at ON transactions (created_at);