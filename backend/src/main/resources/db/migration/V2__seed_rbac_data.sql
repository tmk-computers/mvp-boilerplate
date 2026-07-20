-- Flyway Migration V2: Initial RBAC Seed Data

-- Insert Super Admin & Customer Permission Groups
INSERT INTO permission_groups (id, name, description, created_by) VALUES 
('11111111-1111-1111-1111-111111111111', 'System Management', 'User, Role and Permission administration', 'SYSTEM'),
('22222222-2222-2222-2222-222222222222', 'Customer Management', 'Customer domain operations', 'SYSTEM');

-- Insert Core Granular Permissions
INSERT INTO permissions (id, permission_group_id, name, description, created_by) VALUES
('a1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'User.View', 'View User records', 'SYSTEM'),
('a2222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111', 'User.Create', 'Create new Users', 'SYSTEM'),
('a3333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 'User.Update', 'Update existing Users', 'SYSTEM'),
('a4444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111', 'User.Delete', 'Delete Users', 'SYSTEM'),
('b1111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', 'Customer.View', 'View Customers', 'SYSTEM'),
('b2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'Customer.Create', 'Create Customer', 'SYSTEM'),
('b3333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222', 'Customer.Update', 'Update Customer', 'SYSTEM'),
('b4444444-4444-4444-4444-444444444444', '22222222-2222-2222-2222-222222222222', 'Customer.Delete', 'Delete Customer', 'SYSTEM'),
('b5555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222', 'Customer.Export', 'Export Customer Data', 'SYSTEM'),
('b6666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', 'Customer.Approve', 'Approve Customer Profiles', 'SYSTEM');

-- Insert Standard System Roles
INSERT INTO roles (id, name, description, created_by) VALUES
('c1111111-1111-1111-1111-111111111111', 'ROLE_SUPER_ADMIN', 'Super Administrator with unrestricted privileges', 'SYSTEM'),
('c2222222-2222-2222-2222-222222222222', 'ROLE_ADMIN', 'System Administrator', 'SYSTEM'),
('c3333333-3333-3333-3333-333333333333', 'ROLE_USER', 'Standard End User', 'SYSTEM');

-- Assign Permissions to SUPER_ADMIN
INSERT INTO role_permissions (role_id, permission_id)
SELECT 'c1111111-1111-1111-1111-111111111111', id FROM permissions;

-- Insert Default Super Admin User (Password: Admin@123 -> BCrypt Hash)
INSERT INTO users (id, email, password_hash, first_name, last_name, is_active, created_by) VALUES
('d1111111-1111-1111-1111-111111111111', 'admin@dasp.com', '$2a$12$K8a8qS5d7R8x9Z1y2w3v4u5t6s7r8q9p0o1n2m3l4k5j6i7h8g9f0', 'Super', 'Admin', TRUE, 'SYSTEM');

-- Link Super Admin User to SUPER_ADMIN Role
INSERT INTO user_roles (user_id, role_id) VALUES
('d1111111-1111-1111-1111-111111111111', 'c1111111-1111-1111-1111-111111111111');

-- Seed System Settings
INSERT INTO system_settings (setting_key, setting_value, description, created_by) VALUES
('SITE_NAME', 'DASP Digital Enterprise Platform', 'Application Public Name', 'SYSTEM'),
('MAX_FILE_UPLOAD_SIZE_MB', '25', 'Maximum allowed single file upload size in MB', 'SYSTEM');
